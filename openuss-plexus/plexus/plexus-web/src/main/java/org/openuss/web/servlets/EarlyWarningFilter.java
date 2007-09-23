package org.openuss.web.servlets;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * This class buffers output to a servlet response and attempts to prevent dodgy
 * write-before-redirect errors. Any attempt to perform something that would
 * reach an illegal state causes the buffer to be reset and the position of both
 * the first write and the subsequent illegal operation to be logged. The one
 * exception is if you do a 'flush()', required for some of the screens that
 * dump logging output. Those cause the response to be committed early whatever
 * happens.
 */
public class EarlyWarningFilter implements Filter {

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			InstrumentedResponse instrumentedResponse = new InstrumentedResponse((HttpServletResponse) response);
			chain.doFilter(request, instrumentedResponse);
 			instrumentedResponse.flush();
		} else {
			chain.doFilter(request, response);
		}
	}

	private static class InstrumentedResponse extends HttpServletResponseWrapper {
		private InstrumentedPrintWriter writer;
		private InstrumentedServletOutputStream stream;
		private boolean committed = false;
		private IllegalStateException thrown = null;

		public InstrumentedResponse(HttpServletResponse httpServletResponse) {
			super(httpServletResponse);
		}

		public void sendError(int i, String name) throws IOException {
			commit();
			resetWithWarning("tried to sendError() after write()");
			super.sendError(i, name);
		}

		private void commit() {
			if (!committed) {
				committed = true;
				thrown = new IllegalStateException("A second call to a commit method occurred, this was first:");
			} else {
				thrown.printStackTrace();
			}
		}

		public void sendError(int i) throws IOException {
			commit();
			resetWithWarning("tried to sendError() after write()");
			super.sendError(i);
		}

		public void sendRedirect(String name) throws IOException {
			commit();
			resetWithWarning("tried to sendRedirect() after write()");
			super.sendRedirect(name);
		}

		public ServletOutputStream getOutputStream() throws IOException {
			if (stream == null) {
				stream = new InstrumentedServletOutputStream(super.getOutputStream());
			}
			return stream;
		}

		public void reset() {
			resetWithWarning("Tried to reset() response after write");
			super.reset();
		}

		public void resetBuffer() {
            resetWithWarning("Tried to resetBuffer() response after write (this might be ok!)");
            super.resetBuffer();
        }

		public void flushBuffer() throws IOException {
			commit();
			super.flushBuffer();
		}

		public PrintWriter getWriter() throws IOException {
			if (writer == null) {
				writer = new InstrumentedPrintWriter(super.getWriter());
			}
			return writer;
		}

		private void resetWithWarning(String warning) {
			if (stream != null) {
				if (stream.isWritten()) {
					stream.getThrown().printStackTrace();
					new IllegalStateException(warning).printStackTrace();
					stream.reset();
					stream = null;
				}
			}
			if (writer != null) {
				if (writer.isWritten()) {
					writer.getThrown().printStackTrace();
					new IllegalStateException(warning).printStackTrace();
					writer.reset();
					writer = null;
				}
			}
		}

		public void flush() throws IOException {
			if (writer != null && writer.written) {
				commit();
				writer.flush();
			}
			if (stream != null && stream.written) {
				commit();
				stream.flush();
			}
		}
	}

	private static class InstrumentedPrintWriter extends PrintWriter {
		private PrintWriter original;
		private CharArrayWriter buffer;
		private boolean written = false;
		private IllegalStateException thrown;

		protected InstrumentedPrintWriter(PrintWriter original) {
			super(original);
			this.original = original;
			this.buffer = new CharArrayWriter();
		}

		public void close() {
			original.write(buffer.toCharArray());
			buffer.reset();
			super.close();
		}

		public void flush() {
			original.write(buffer.toCharArray());
			buffer.reset();
			super.flush();
		}

		public boolean isWritten() {
			return written;
		}

		public IllegalStateException getThrown() {
			return thrown;
		}

		public void write(int b) {
            if (!written) {
                written = true;
                thrown = new IllegalStateException("This write may have been too early");
            }
            buffer.write(b);
        }

		public void write(char buf[], int off, int len) {
            if (!written) {
                written = true;
                thrown = new IllegalStateException("This write may have been too early");
            }
            buffer.write(buf, off, len);
        }

		public void write(String s, int off, int len) {
            if (!written) {
                written = true;
                thrown = new IllegalStateException("This write may have been too early");
            }
            buffer.write(s, off, len);
        }

		public void println() {
            if (!written) {
                written = true;
                thrown = new IllegalStateException("This write may have been too early");
            }
            buffer.write('\n');
        }

		public void reset() {
			buffer.reset();
		}
	}

	private static class InstrumentedServletOutputStream extends ServletOutputStream {
		private ServletOutputStream original;
		private ByteArrayOutputStream buffer;
		private boolean written = false;
		private IllegalStateException thrown;

		protected InstrumentedServletOutputStream(ServletOutputStream original) {
			this.original = original;
			this.buffer = new ByteArrayOutputStream();
		}

		public void close() throws IOException {
			original.write(buffer.toByteArray());
			buffer.reset();
			super.close();
		}

		public void flush() throws IOException {
			original.write(buffer.toByteArray());
			buffer.reset();
			super.flush();
		}

		public boolean isWritten() {
			return written;
		}

		public IllegalStateException getThrown() {
			return thrown;
		}

		public void write(int b) throws IOException {
            if (!written) {
                written = true;
                thrown = new IllegalStateException("This write happened before a redirect");
            }
            buffer.write(b);
        }

		public void reset() {
			buffer.reset();
		}
	}
}