package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.MyUniCourseInfo;
import org.openuss.desktop.MyUniDepartmentInfo;
import org.openuss.desktop.MyUniInfo;
import org.openuss.desktop.MyUniInstituteInfo;
import org.openuss.desktop.MyUniUniversityInfo;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.framework.jsfcontrols.components.flexlist.CourseUIFlexList;
import org.openuss.framework.jsfcontrols.components.flexlist.GroupUIFlexList;
import org.openuss.framework.jsfcontrols.components.flexlist.ListItemDAO;
import org.openuss.framework.jsfcontrols.components.flexlist.UIFlexList;
import org.openuss.framework.jsfcontrols.components.flexlist.UITabs;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Display of the Startpage, after the user logged in.
 * 
 * @author Peter Grosskopf
 * @author Julian Reimann
 */
@Bean(name = "views$secured$myuni$myuni", scope = Scope.REQUEST)
@View
public class MyUniPage extends BasePage {
	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	@Property(value = "#{discussionService}")
	protected DiscussionService discussionService;

	@Property(value = "#{courseNewsletterService}")
	protected CourseNewsletterService courseNewsletterService;

	@Property(value = "#{courseDao}")
	protected CourseDao courseDao;

	@Property(value = "#{securityService}")
	protected SecurityService securityService;

	private static final String universityBasePath = "/views/public/university/university.faces?university=";

	@Property(value = "#{courseService}")
	protected CourseService courseService;

	@Property(value = "#{groupService}")
	protected GroupService groupService;

	private Long paramUniversity = null;
	private Long paramRemoveDepartment = null;
	private Long paramRemoveInstitute = null;
	private Long paramRemoveCourse = null;
	private Long paramLeaveGroup = null;
	private Long paramSubscribeNewsletter = null;
	private Long paramUnsubscribeNewsletter = null;
	private Long paramSubscribeForum = null;
	private Long paramUnsubscribeForum = null;
	private UIFlexList departmentsList;
	private UIFlexList institutesList;
	private CourseUIFlexList coursesList;
	private GroupUIFlexList groupsList;
	private UITabs tabs;
	private Desktop desktop;

	private List<UserGroupInfo> groups;

	private boolean prerenderCalled = false;
	private boolean tabDataLoaded = false;
	private boolean instituteListDataLoaded = false;
	private boolean departmentListDataLoaded = false;
	private boolean courseListDataLoaded = false;

	private Map<Long, MyUniInfo> myUniData;

	private static final String myUniBasePath = "/views/secured/myuni/myuni.faces";
	private static final String departmentsBasePath = "/views/public/department/department.faces";
	private static final String institutesBasePath = "/views/public/institute/institute.faces";
	private static final String coursesBasePath = "/views/secured/course/main.faces";
	private static final String groupBasePath = "/views/secured/groups/components/main.faces?group=";

	ValueBinding binding = getFacesContext().getApplication()
			.createValueBinding("#{visit.locale}");
	String locale = (String) binding.getValue(getFacesContext());
	ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(
			locale));

	@Prerender
	public void prerender() {
		logger.debug("Prerender MyUni-Page");
		prerenderCalled = true;
		refreshDesktop();

		// Load paramenters from request
		loadParams();
		// Remove bookmarks
		removeBookmarks();
		// (un)subscribe newsletter/forum
		handleSubscriptions();
		// Load myUni data
		prepareData();
		// Load data into the list components
		loadValuesForComponents();
		breadcrumbs.loadMyUniCrumbs();
	}

	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktopInfo == null) {
					logger.error("No desktop found for user "
							+ user.getUsername() + ". Create new one.");
					desktopInfo = desktopService2.findDesktopByUser(user
							.getId());

				} else {
					logger.debug("refreshing desktop data");
					desktopInfo = desktopService2.findDesktop(desktopInfo
							.getId());
				}
				setSessionBean(Constants.DESKTOP_INFO, desktopInfo);

				assert desktopDao != null;
				desktop = desktopDao.load(desktopInfo.getId());
				assert desktop != null;

			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	/*
	 * Loads parameters from request object
	 */
	private void loadParams() {
		logger.debug("Loading request parameters");
		Map<?, ?> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		try {
			String stringParamUniversity = (String) params.get("university");
			paramUniversity = Long.valueOf(stringParamUniversity);
		} catch (Exception e) {
			paramUniversity = null;
		}

		try {
			String stringParamRemoveCourse = (String) params
					.get("remove_course");
			paramRemoveCourse = Long.valueOf(stringParamRemoveCourse);
		} catch (Exception e) {
			paramRemoveCourse = null;
		}

		try {
			String stringParamRemoveDepartment = (String) params
					.get("remove_department");
			paramRemoveDepartment = Long.valueOf(stringParamRemoveDepartment);
		} catch (Exception e) {
			paramRemoveDepartment = null;
		}

		try {
			String stringParamRemoveInstitute = (String) params
					.get("remove_institute");
			paramRemoveInstitute = Long.valueOf(stringParamRemoveInstitute);
		} catch (Exception e) {
			paramRemoveInstitute = null;
		}
		try {
			String stringParamSubscribeNewsletter = (String) params
					.get("subscribe_newsletter");
			paramSubscribeNewsletter = Long
					.valueOf(stringParamSubscribeNewsletter);
		} catch (Exception e) {
			paramSubscribeNewsletter = null;
		}
		try {
			String stringParamUnsubscribeNewsletter = (String) params
					.get("unsubscribe_newsletter");
			paramUnsubscribeNewsletter = Long
					.valueOf(stringParamUnsubscribeNewsletter);
		} catch (Exception e) {
			paramUnsubscribeNewsletter = null;
		}
		try {
			String stringParamSubscribeForum = (String) params
					.get("subscribe_forum");
			paramSubscribeForum = Long.valueOf(stringParamSubscribeForum);
		} catch (Exception e) {
			paramSubscribeForum = null;
		}
		try {
			String stringParamUnsubscribeForum = (String) params
					.get("unsubscribe_forum");
			paramUnsubscribeForum = Long.valueOf(stringParamUnsubscribeForum);
		} catch (Exception e) {
			paramUnsubscribeForum = null;
		}
		try {
			String stringParamLeaveGroup = (String) params.get("leave_group");
			paramLeaveGroup = Long.valueOf(stringParamLeaveGroup);
		} catch (Exception e) {
			paramLeaveGroup = null;
		}
	}

	/*
	 * Removes bookmarks if the corresponding parameters are set
	 */
	private void removeBookmarks() {
		if (user != null && desktopService2 != null) {
			Long desktopId = null;

			// Get the desktop id
			try {
				DesktopInfo desktopInfo = desktopService2
						.findDesktopByUser(user.getId());
				desktopId = desktopInfo.getId();
			} catch (Exception e) {
				logger.error(e);
			}

			if (desktopId != null) {
				// Remove department bookmark
				if (paramRemoveDepartment != null) {
					try {
						desktopService2.unlinkDepartment(desktopId,
								paramRemoveDepartment);
					} catch (Exception e) {
						logger.error(e);
					}
				}

				// Remove institute bookmark
				if (paramRemoveInstitute != null) {
					try {
						desktopService2.unlinkInstitute(desktopId,
								paramRemoveInstitute);
					} catch (Exception e) {
						logger.error(e);
					}
				}

				// Remove course bookmark
				if (paramRemoveCourse != null) {
					try {
						desktopService2.unlinkCourse(desktopInfo.getId(),
								paramRemoveCourse);
					} catch (Exception e) {
						logger.error(e);
					}
				}

				// Leave group
				if (paramLeaveGroup != null) {
					try {
						leaveGroup(paramLeaveGroup);
					} catch (Exception e) {
						logger.error(e);
					}

				}
			}
		}
	}

	/*
	 * Handles newsletter and forum subscriptions
	 */
	private void handleSubscriptions() {
		User user = getSecurityService().getCurrentUser();
		if (paramSubscribeNewsletter != null) {
			CourseInfo ci = getCourseDao().toCourseInfo(
					getCourseDao().load(paramSubscribeNewsletter));
			getCourseNewsletterService().subscribe(ci, user);
		}
		if (paramUnsubscribeNewsletter != null) {
			CourseInfo ci = getCourseDao().toCourseInfo(
					getCourseDao().load(paramUnsubscribeNewsletter));
			getCourseNewsletterService().unsubscribe(ci, user);
		}
		if (paramSubscribeForum != null) {
			CourseInfo ci = getCourseDao().toCourseInfo(
					getCourseDao().load(paramSubscribeForum));
			ForumInfo forum = getDiscussionService().getForum(ci);
			getDiscussionService().addForumWatch(forum);
		}
		if (paramUnsubscribeForum != null) {
			CourseInfo ci = getCourseDao().toCourseInfo(
					getCourseDao().load(paramUnsubscribeForum));
			ForumInfo forum = getDiscussionService().getForum(ci);
			getDiscussionService().removeForumWatch(forum);
		}
	}

	public boolean getData() {
		logger.debug("Checking if data available");
		if ((myUniData == null) || myUniData.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Loads myUni data if myUniData is not already set
	 */
	public void prepareData() {
		logger.debug("Preparing MyUni data");
		if (myUniData == null) {
			try {
				myUniData = (Map<Long, MyUniInfo>) desktopService2
						.getMyUniInfo(user.getId());
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	/*
	 * Called by the prerender method after bookmarks have been removed to make
	 * sure that the current state of bookmarks is shown. Otherwise removed
	 * bookmarks only disappear upon the next request.
	 */
	private void loadValuesForComponents() {
		if (tabs != null)
			loadValuesForTabs(tabs);

		if (departmentsList != null)
			loadValuesForDepartmentList(departmentsList);

		if (institutesList != null)
			loadValuesForInstituteList(institutesList);

		if (coursesList != null)
			loadValuesForCourseList(coursesList);

		if (groupsList != null)
			loadValuesForGroupsList(groupsList);
	}

	private void loadValuesForDepartmentList(UIFlexList departmentsList) {
		if (departmentListDataLoaded == false && prerenderCalled == true
				&& departmentsList != null) {
			logger.debug("Loading data for departments flexlist");
			// Make sure myUni-Data is loaded
			prepareData();

			// Get the current university id
			Long universityId = chooseUniversity();

			// Put data in the component's attributes
			if (universityId != null && myUniData != null) {
				departmentsList.getAttributes().put("visibleItems",
						getDepartmentListItems(universityId));

				// Make sure this isn't executed twice
				departmentListDataLoaded = true;
			}
		}
	}

	private void loadValuesForInstituteList(UIFlexList institutesList) {
		if (instituteListDataLoaded == false && prerenderCalled == true
				&& institutesList != null) {
			logger.debug("Loading data for institutes flexlist");
			// Make sure myUni-Data is loaded
			prepareData();

			// Get the current university id
			Long universityId = chooseUniversity();

			// Put data in the component's attributes
			if (universityId != null && myUniData != null) {
				institutesList.getAttributes().put("visibleItems",
						getVisibleInstituteListItems(universityId));
				institutesList.getAttributes().put("hiddenItems",
						getHiddenInstituteListItems(universityId));

				// Make sure this isn't executed twice
				instituteListDataLoaded = true;
			}
		}
	}

	private void loadValuesForCourseList(CourseUIFlexList coursesList) {
		if (courseListDataLoaded == false && prerenderCalled == true
				&& coursesList != null) {
			logger.debug("Loading data for courses flexlist");
			// Make sure myUni-Data is loaded
			prepareData();

			// Get the current university id
			Long universityId = chooseUniversity();

			// Put data in the component's attributes
			if (universityId != null && myUniData != null) {
				coursesList.getAttributes().put("visibleItems",
						getVisibleCourseListItems(universityId));
				coursesList.getAttributes().put("hiddenItems",
						getHiddenCourseListItems(universityId));

				// Make sure this isn't executed twice
				courseListDataLoaded = true;
			}
		}
	}

	private void loadValuesForTabs(UITabs tabs) {
		if (tabDataLoaded == false && prerenderCalled == true && tabs != null) {
			logger.debug("Loading data for MyUni-Tabs");
			// Make sure myUni-Data is loaded
			prepareData();

			// Get the current university id
			Long universityId = chooseUniversity();

			if (universityId != null && myUniData != null) {
				// Prepare list of ListItemDAOs for the tab component
				MyUniUniversityInfo universityInfo;
				ListItemDAO newItem;
				ListItemDAO currentItem = null;
				List<ListItemDAO> items = new ArrayList<ListItemDAO>();

				for (MyUniInfo myUniInfo : myUniData.values()) {
					universityInfo = myUniInfo.getMyUniUniversityInfo();

					if (universityInfo != null) {
						newItem = new ListItemDAO();
						newItem.setTitle(universityInfo.getName());
						newItem.setUrl(contextPath() + myUniBasePath
								+ "?university="
								+ universityInfo.getId().toString());

						if (universityId != null
								&& universityId.longValue() == universityInfo
										.getId().longValue()) {
							currentItem = newItem;
							currentItem.setUrl(contextPath()
									+ universityBasePath + universityId);
						} else
							items.add(newItem);
					}
				}

				// Put data in the component's attributes
				tabs.getAttributes().put("currentItem", currentItem);
				tabs.getAttributes().put("items", items);

				// Make sure this isn't executed twice
				tabDataLoaded = true;
			}
		}
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the departments flexlist
	 */
	private List<ListItemDAO> getDepartmentListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniDepartmentInfo> departmentCollection = myUniInfo
						.getDepartments();

				for (MyUniDepartmentInfo departmentInfo : departmentCollection) {
					newItem = new ListItemDAO();
					newItem.setTitle(departmentInfo.getName());
					newItem.setUrl(contextPath() + departmentsBasePath
							+ "?department=" + departmentInfo.getId());
					if (departmentInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(contextPath()
								+ myUniBasePath + "?university=" + universityId
								+ "&remove_department="
								+ departmentInfo.getId());

					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the institutes flexlist
	 */
	private List<ListItemDAO> getVisibleInstituteListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniInstituteInfo> instituteCollection = myUniInfo
						.getCurrentInstitutes();

				for (MyUniInstituteInfo instituteInfo : instituteCollection) {
					newItem = new ListItemDAO();
					newItem.setTitle(instituteInfo.getName());
					newItem.setUrl(contextPath() + institutesBasePath
							+ "?institute=" + instituteInfo.getId());
					Integer numberOfCurrentCourses = instituteInfo
							.getNumberOfCurrentCourses();
					if (numberOfCurrentCourses != null
							&& numberOfCurrentCourses > 0)
						if (numberOfCurrentCourses.equals(1))
							newItem
									.setMetaInformation(numberOfCurrentCourses
											.toString()
											+ " "
											+ i18n("MYUNI_INSITUTE_COURSECOUNT_STRING_SINGULAR"));
						else
							newItem
									.setMetaInformation(numberOfCurrentCourses
											.toString()
											+ " "
											+ i18n("MYUNI_INSITUTE_COURSECOUNT_STRING"));
					if (instituteInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(contextPath()
								+ myUniBasePath + "?university=" + universityId
								+ "&remove_institute=" + instituteInfo.getId());

					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the departments flexlist in the hidden list
	 */
	private List<ListItemDAO> getHiddenInstituteListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniInstituteInfo> instituteCollection = myUniInfo
						.getPastInstitutes();

				for (MyUniInstituteInfo instituteInfo : instituteCollection) {
					newItem = new ListItemDAO();
					newItem.setTitle(instituteInfo.getName());
					newItem.setUrl(contextPath() + institutesBasePath
							+ "?institute=" + instituteInfo.getId());
					if (instituteInfo.isBookmarked()) {
						newItem.setRemoveBookmarkUrl(myUniBasePath
								+ "?university=" + universityId
								+ "&remove_institute=" + instituteInfo.getId());
					}
					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the courses flexlist
	 */
	private List<ListItemDAO> getVisibleCourseListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniCourseInfo> courseCollection = myUniInfo
						.getCurrentCourses();

				for (MyUniCourseInfo courseInfo : courseCollection) {

					newItem = new ListItemDAO();
					newItem.setTitle(courseInfo.getName());
					newItem.setUrl(contextPath() + coursesBasePath + "?course="
							+ courseInfo.getId());
					newItem.setRemoveBookmarkUrl(contextPath() + myUniBasePath
							+ "?university=" + universityId + "&remove_course="
							+ courseInfo.getId());
					// Newsletter subscribe-status
					Boolean newsletterSubscribed = courseInfo
							.getNewsletterSubscribed();
					newItem.setNewsletterSubscribed(newsletterSubscribed);
					if (newsletterSubscribed != null) {
						newItem
								.setNewsletterActionUrl(contextPath()
										+ myUniBasePath
										+ "?university="
										+ universityId
										+ (newsletterSubscribed ? "&unsubscribe_newsletter="
												: "&subscribe_newsletter=")
										+ courseInfo.getId());
					}
					// Forum subscribe-status
					Boolean forumSubscribed = courseInfo.getForumSubscribed();
					newItem.setForumSubscribed(forumSubscribed);
					if (forumSubscribed != null) {
						newItem.setForumActionUrl(contextPath()
								+ myUniBasePath
								+ "?university="
								+ universityId
								+ (forumSubscribed ? "&unsubscribe_forum="
										: "&subscribe_forum=")
								+ courseInfo.getId());
					}

					newItem.setMetaInformation(courseInfo.getPeriod());
					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the courses flexlist in the hidden list
	 */
	private List<ListItemDAO> getHiddenCourseListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniCourseInfo> courseCollection = myUniInfo
						.getPastCourses();

				for (MyUniCourseInfo courseInfo : courseCollection) {

					newItem = new ListItemDAO();
					newItem.setTitle(courseInfo.getName());
					newItem.setUrl(contextPath() + coursesBasePath + "?course="
							+ courseInfo.getId());
					newItem.setRemoveBookmarkUrl(contextPath() + myUniBasePath
							+ "?university=" + universityId + "&remove_course="
							+ courseInfo.getId());
					// Newsletter subscribe-status
					Boolean newsletterSubscribed = courseInfo
							.getNewsletterSubscribed();
					newItem.setNewsletterSubscribed(newsletterSubscribed);
					if (newsletterSubscribed != null) {
						newItem
								.setNewsletterActionUrl(contextPath()
										+ myUniBasePath
										+ "?university="
										+ universityId
										+ (newsletterSubscribed ? "&unsubscribe_newsletter="
												: "&subscribe_newsletter=")
										+ courseInfo.getId());
					}
					// Forum subscribe-status
					Boolean forumSubscribed = courseInfo.getForumSubscribed();
					newItem.setForumSubscribed(forumSubscribed);
					if (forumSubscribed != null) {
						newItem.setForumActionUrl(contextPath()
								+ myUniBasePath
								+ "?university="
								+ universityId
								+ (forumSubscribed ? "&unsubscribe_forum="
										: "&subscribe_forum=")
								+ courseInfo.getId());
					}

					newItem.setMetaInformation(courseInfo.getPeriod());
					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}

	/*
	 * Selects a university to be shown on the MyUni page if no university
	 * parameter is given
	 */
	private Long chooseUniversity() {
		if (myUniData != null) {
			// Return the university parameter if it is contained in out data
			// set
			if (paramUniversity != null)
				if (myUniData.containsKey(paramUniversity))
					return paramUniversity;

			// Return the university id in our data set
			Iterator<MyUniInfo> iterator = myUniData.values().iterator();

			if (iterator.hasNext()) {
				MyUniInfo firstUni = iterator.next();

				if (firstUni != null) {
					MyUniUniversityInfo uniInfo = firstUni
							.getMyUniUniversityInfo();
					if (uniInfo != null)
						return uniInfo.getId();
				}
			}
		}

		return null;
	}

	public UIFlexList getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(UIFlexList departmentsList) {
		logger.debug("Setting departments flexlist component");
		this.departmentsList = departmentsList;
		departmentsList.getAttributes().put("title",
				bundle.getString("flexlist_departments"));
		departmentsList.getAttributes().put("showButtonTitle",
				bundle.getString("flexlist_more_departments"));
		departmentsList.getAttributes().put("hideButtonTitle",
				bundle.getString("flexlist_less_departments"));
		departmentsList.getAttributes().put("alternateRemoveBookmarkLinkTitle",
				bundle.getString("flexlist_remove_bookmark"));

		// Load values into the component
		loadValuesForDepartmentList(departmentsList);
	}

	public UIFlexList getInstitutesList() {
		return institutesList;
	}

	public void setInstitutesList(UIFlexList institutesList) {
		logger.debug("Setting institutes flexlist component");
		this.institutesList = institutesList;
		institutesList.getAttributes().put("title",
				bundle.getString("flexlist_institutes"));
		institutesList.getAttributes().put("showButtonTitle",
				bundle.getString("flexlist_more_institutes"));
		institutesList.getAttributes().put("hideButtonTitle",
				bundle.getString("flexlist_less_institutes"));
		institutesList.getAttributes().put("alternateRemoveBookmarkLinkTitle",
				bundle.getString("flexlist_remove_bookmark"));

		// Load values into the component
		loadValuesForInstituteList(institutesList);
	}

	public CourseUIFlexList getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(CourseUIFlexList coursesList) {
		logger.debug("Setting courses flexlist component");
		this.coursesList = coursesList;
		coursesList.getAttributes().put("title",
				bundle.getString("flexlist_courses"));
		coursesList.getAttributes().put("showButtonTitle",
				bundle.getString("flexlist_more_courses"));
		coursesList.getAttributes().put("hideButtonTitle",
				bundle.getString("flexlist_less_courses"));
		coursesList.getAttributes().put("alternateRemoveBookmarkLinkTitle",
				bundle.getString("flexlist_remove_bookmark"));

		// Load values into the component
		loadValuesForCourseList(coursesList);
	}

	public UITabs getTabs() {
		return tabs;
	}

	public void setTabs(UITabs tabs) {
		logger.debug("Setting MyUni-tabs component");
		this.tabs = tabs;
		tabs.getAttributes().put("alternateLinkTitle",
				i18n("flexlist_tabs_details"));

		loadValuesForTabs(tabs);
	}

	/* ----- groups flexlist ----- */

	public GroupUIFlexList getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(GroupUIFlexList groupsList) {
		logger.debug("Setting groups flexlist component");
		this.groupsList = groupsList;
		groupsList.getAttributes().put("title", i18n("flexlist_groups"));
		groupsList.getAttributes().put("showButtonTitle",
				i18n("flexlist_more_groups"));
		groupsList.getAttributes().put("hideButtonTitle",
				i18n("flexlist_less_groups"));
		// groupsList.getAttributes().put("alternateRemoveBookmarkLinkTitle",
		// i18n("flexlist_remove_bookmark"));
		// Load values into the component
		loadValuesForGroupsList(groupsList);
	}

	private void loadValuesForGroupsList(GroupUIFlexList groupsList) {
		logger.debug("Loading data for groups flexlist");
		groupsList.getAttributes().put("visibleItems", getGroupsListItems());
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the groups flexlist
	 */
	private List<ListItemDAO> getGroupsListItems() {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		ListItemDAO newItem;
		Long universityId = chooseUniversity();
		List<UserGroupInfo> groupInfos = getGroups();
		for (UserGroupInfo groupInfo : groupInfos) {
			newItem = new ListItemDAO();
			newItem.setTitle(groupInfo.getName());
			newItem.setUrl(contextPath() + groupBasePath + groupInfo.getId());
			newItem.setLeaveGroupUrl(contextPath() + myUniBasePath
					+ "?university=" + universityId + "&leave_group="
					+ groupInfo.getId());
			listItems.add(newItem);
		}
		return listItems;
	}

	private List<UserGroupInfo> getGroups() {
		if (groups == null) {
			groups = groupService.getGroupsByUser(user.getId());
			logger.debug("User GROUPS: " + groups.size() + ", " + groups);
			for (UserGroupInfo group : groups) {
				logger.debug("Gruppe Creator:" + group.getCreator() + " - "
						+ group.getCreatorName());
			}
		}
		return groups;
	}

	public void leaveGroup(Long id) {
		logger.debug("member leave group");
		UserGroupInfo group = groupService.getGroupInfo(id);
		if ((groupService.getModerators(group).size() == 1)
				&& groupService.isModerator(group, user.getId())) {
			if (groupService.getAllMembers(group).size() == 1) {
				groupService.deleteUserGroup(group);
				addMessage(i18n("message_group_deleted", group.getName()));
			} else {
				addError(i18n("group_last_moderator_error"));
			}
		} else {
			groupService.removeMember(group, user.getId());
			addMessage(i18n("message_group_left", group.getName()));
		}
		groups = null;
	}

	public boolean getGroupData() {
		return (groupService.getGroupsByUser(user.getId()).size() == 0);
	}

	/* ----- getter and setter ----- */

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}

	public CourseNewsletterService getCourseNewsletterService() {
		return courseNewsletterService;
	}

	public void setCourseNewsletterService(
			CourseNewsletterService courseNewsletterService) {
		this.courseNewsletterService = courseNewsletterService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
