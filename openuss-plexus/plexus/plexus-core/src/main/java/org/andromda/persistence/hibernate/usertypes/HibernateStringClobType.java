package org.andromda.persistence.hibernate.usertypes;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * <p>
 * A hibernate user type which converts a Clob into a String and back again.
 * </p>
 */
public class HibernateStringClobType
    implements UserType
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 10000L;

    /**
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes()
    {
        return new int[] {Types.CLOB};
    }

    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public Class returnedClass()
    {
        return String.class;
    }

    /**
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(
        Object x,
        Object y)
        throws HibernateException
    {
        boolean equal = false;
        if (x == null || y == null)
        {
            equal = false;
        }
        else if (!(x instanceof String) || !(y instanceof String))
        {
            equal = false;
        }
        else
        {
            equal = ((String)x).equals(y);
        }
        return equal;
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(
        ResultSet resultSet,
        String[] names,
        Object owner)
        throws HibernateException, SQLException
    {
        final StringBuffer buffer = new StringBuffer();
        try
        {
            //First we get the stream
            Reader inputStream = resultSet.getCharacterStream(names[0]);
            if (inputStream == null)
            {
                return null;
            }
            char[] buf = new char[1024];
            int read = -1;

            while ((read = inputStream.read(buf)) > 0)
            {
                buffer.append(new String(
                        buf,
                        0,
                        read));
            }
            inputStream.close();
        }
        catch (IOException exception)
        {
            throw new HibernateException("Unable to read from resultset", exception);
        }
        return buffer.toString();
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     */
    public void nullSafeSet(
        PreparedStatement preparedStatement,
        Object data,
        int index)
        throws HibernateException, SQLException
    {
        if (data != null)
        {
            StringReader r = new StringReader((String)data);
            preparedStatement.setCharacterStream(
                index,
                r,
                ((String)data).length());
        }
        else
        {
            preparedStatement.setNull(
                index,
                sqlTypes()[0]);
        }
    }

    /**
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object value)
        throws HibernateException
    {
        String ret = null;
        value = value == null ? new String() : value;
        String in = (String)value;
        int len = in.length();
        char[] buf = new char[len];

        for (int i = 0; i < len; i++)
        {
            buf[i] = in.charAt(i);
        }
        ret = new String(buf);
        return ret;
    }

    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable()
    {
        return false;
    }

    /**
     * @see org.hibernate.usertype.UserType#replace(Object original, Object target, Object owner)
     */
    public Object replace(Object original, Object target, Object owner)
    {
        return this.deepCopy(original);
    }

    /**
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable cached, Object owner)
     */
    public Object assemble(java.io.Serializable cached, Object owner)
    {
        return this.deepCopy(cached);
    }

    /**
     * @see org.hibernate.usertype.UserType#assemble(Object value)
     */
     public java.io.Serializable disassemble(Object value)
     {
        return (java.io.Serializable)value;
     }

     /**
     * @see org.hibernate.usertype.UserType#assemble(Object value)
     */
     public int hashCode(Object x)
     {
        return x.hashCode();
     }
}