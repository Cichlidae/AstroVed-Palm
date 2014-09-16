package com.premaservices.astroved.palm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.premaservices.astroved.palm.controller.editor.DashaPropertyEditor;
import com.premaservices.astroved.palm.controller.editor.DatePropertyEditor;
import com.premaservices.astroved.palm.controller.editor.EnumPropertyEditor;
import com.premaservices.astroved.palm.controller.view.StudentListExcelBuilder;
import com.premaservices.astroved.palm.controller.view.StudentPortfolioPDFBuilder;
import com.premaservices.astroved.palm.dao.EqualStudentFilter;
import com.premaservices.astroved.palm.dao.StudentFilter;
import com.premaservices.astroved.palm.dao.StudentFilterPack;
import com.premaservices.astroved.palm.model.Course;
import com.premaservices.astroved.palm.model.Student;
import com.premaservices.astroved.palm.service.ApplicationMessageSource;
import com.premaservices.astroved.palm.service.RegionService;
import com.premaservices.astroved.palm.service.StudentService;

import ru.premaservices.extention.spring.annotation.SessionAttribute;
import ru.premaservices.util.ApplicationSecurityManager;
import ru.premaservices.util.CommonUtil;
import ru.premaservices.util.FileUtil;
import ru.premaservices.util.ServletUtil;

@Controller
public class StudentController {
	
	public static final String STUDENT_FORM_REQUEST = "/student/add";
	public static final String STUDENT_EDIT_REQUEST = "/student/edit";
	public static final String STUDENT_VIEW_REQUEST = "/student/view";
	public static final String STUDENT_AVATAR_REQUEST = "/student/avatar";
	public static final String STUDENT_DETAILS_REQUEST = "/student/details";
	public static final String STUDENT_PAYMENT_REQUEST = "/student/payment";
	public static final String STUDENT_NOTES_REQUEST = "/student/notes";
	public static final String PAYMENT_ADD_REQUEST = "/payment/add";
	public static final String STUDENT_REGISTER_REQUEST = "/student/register";
	public static final String STUDENT_ENROLL_REQUEST = "/student/enroll";
	public static final String STUDENT_EXCLUDE_REQUEST = "/student/exclude";
	public static final String STUDENT_AVATAR_FORM_REQUEST = "/student/upload";
	public static final String STUDENT_SEARCH_REQUEST = "/student/search";
	public static final String STUDENT_FILTER_REQUEST = "/student/filter";
	public static final String STUDENT_TEST_REQUEST = "/student/test";
	public static final String STUDENT_EXAM_REQUEST = "/student/exam";
	public static final String STUDENT_PROCEED_REQUEST = "/student/proceed";
	public static final String EXCEL_STUDENT_LIST_REQUEST = "/student/excel";
	public static final String PDF_STUDENT_REQUEST = "/student/pdf";
	
	public static final String STUDENT_FORM_JSP = "student-form";
	public static final String STUDENT_EDIT_JSP = "student-edit-form";
	public static final String STUDENT_VIEW_JSP = "student-list";
	public static final String STUDENT_DETAILS_JSP = "student-view";
	public static final String STUDENT_PAYMENT_JSP = "student-payment";
	public static final String STUDENT_NOTES_JSP = "student-notes";
	public static final String PAYMENT_FORM_JSP = "payment-form";
	public static final String PAYMENT_INFO_JSP = "payment-info";
	public static final String EXCEPTION_MSG_JSP = "exception-msg";
	public static final String STUDENT_AVATAR_FORM_JSP = "avatar-form";
	public static final String SUCCESS_MSG_JSP = "success-msg";	
	
	public static final String TYPE_MAP_KEY = "type";
	public static final String STUDENT_LIST_KEY = "students";
	public static final String GROUP_LIST_KEY = "groups";
	public static final String COURSE_MAP_KEY = "courses";
	public static final String PAYMENT_MAP_KEY = "payments";
	public static final String PAYMENTINFO_MAP_KEY = "paymentinfo";
	public static final String NOTES_MAP_KEY = "notes";
	public static final String ERROR_KEY = "error";
	public static final String FLAGS_KEY = "flags";
	public static final String MESSAGE_MAP_KEY = "message";
	public static final String FAMILY_FILTER_KEY = "family-filter";
	public static final String COURSE_FILTER_KEY = "course-filter";
	
	public static final String FLAG_ENROLL = "!ENROLL~";
	public static final String FLAG_EXCLUDE = "!EXCLUDE~";
	public static final String FLAG_TEST = "!TEST~";
	public static final String FLAG_EXAM = "!EXAM~";
	public static final String FLAG_PROCEED = "!PROCEED~";
	public static final String FLAG_GRADUATE = "!GRADUATE~";
	
	@Autowired
	private StudentService service;

	@Autowired
	private RegionService regionService;
	
	@Autowired
	ApplicationMessageSource messageSource;
	
	@InitBinder("student")
	public void initBinderForStudent(WebDataBinder binder) {
		 binder.registerCustomEditor(Date.class, new DatePropertyEditor(DatePropertyEditor.DATE_FORMATTER));
		 binder.registerCustomEditor(Course.class, new EnumPropertyEditor<Course>(Course.class));
		 binder.registerCustomEditor(Date.class, "birthdayTime", new DatePropertyEditor(DatePropertyEditor.TIME_FORMATTER));
		 binder.registerCustomEditor(Integer.class, "dashas", new DashaPropertyEditor());
	}
	
	@RequestMapping(value = STUDENT_FORM_REQUEST)
	public String getNewStudentForm (Model model) { //AJAX
		model.addAttribute(new Student());
		model.addAttribute(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		return STUDENT_FORM_JSP;
	}
	
	@RequestMapping(value = STUDENT_EDIT_REQUEST)
	public String getStudentForm (@RequestParam("uid") String uid, Model model) { //AJAX
		Student student = service.getStudent(uid);
		model.addAttribute(student);				
		return STUDENT_EDIT_JSP;
	}
	
	private HashMap<String, String> getCourseMap () {
		Course[] courses = Course.values();
		HashMap<String, String> cMap = new HashMap<String, String>(courses.length);
		for (Course t : courses) {			
			String key = t.toString();
			String value = messageSource.getMessage("str.enum.Course." + key);
			cMap.put(key, value);		
		}
		return cMap;
	}

	@RequestMapping(value = STUDENT_FORM_REQUEST, method = RequestMethod.POST)
	public ModelAndView addStudent (@ModelAttribute("student") @Valid Student student, BindingResult result) { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(STUDENT_FORM_JSP);
			model.addAllObjects(result.getModel());
			model.addObject(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());
			model.addObject(COURSE_MAP_KEY, getCourseMap());	
		}
		else {
			model = new ModelAndView(SUCCESS_MSG_JSP);
			model.addObject(MESSAGE_MAP_KEY, messageSource.getMessage("str.common.student.added"));
			service.updateStudent(student);
		}
		
		return model;
	}
	
	@RequestMapping(value = STUDENT_EDIT_REQUEST, method = RequestMethod.POST)
	public ModelAndView editStudent (@ModelAttribute("student") @Valid Student student, BindingResult result) { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(STUDENT_EDIT_JSP);
			model.addAllObjects(result.getModel());
		}
		else {
			model = new ModelAndView(SUCCESS_MSG_JSP);
			model.addObject(MESSAGE_MAP_KEY, messageSource.getMessage("str.common.student.updated"));
			service.updateStudent(student);
		}
		
		return model;		
	}
	
	@RequestMapping(value = STUDENT_VIEW_REQUEST)
	public String getStudentView (Model model) {
		model.addAttribute(STUDENT_LIST_KEY, service.getStudents());
		model.addAttribute(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());	
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(new StudentFilterPack());			
		return STUDENT_VIEW_JSP;
	}
	
	@RequestMapping(value = STUDENT_DETAILS_REQUEST)
	public String getStudentDetails (@RequestParam("uid") String uid, Model model) {
		Student student = service.getStudent(uid);
		model.addAttribute(student);	
		return STUDENT_DETAILS_JSP;
	}		

	@RequestMapping(value = STUDENT_SEARCH_REQUEST, method = RequestMethod.POST)
	public String searchStudents (@RequestParam("family") String family, Model model) {
		model.addAttribute(STUDENT_LIST_KEY, service.searchByFamily(new EqualStudentFilter("family", family)));
		model.addAttribute(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());	
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(getStudentFilterPack());
		return STUDENT_VIEW_JSP;		
	}
	
	@RequestMapping(value = STUDENT_FILTER_REQUEST, method = RequestMethod.POST)
	public String filterStudents (@ModelAttribute("studentFilterArray") StudentFilterPack filter, Model model) {	
		model.addAttribute(STUDENT_LIST_KEY, service.getStudents(filter));
		model.addAttribute(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());	
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(getStudentFilterPack());		
		return STUDENT_VIEW_JSP;
	}
	
	@RequestMapping(value = STUDENT_SEARCH_REQUEST, method = RequestMethod.POST)
	public String filterHiddenStudents (Model model) {
		model.addAttribute(STUDENT_LIST_KEY, service.getHiddenStudents());
		model.addAttribute(RegionController.COUNTRY_LIST_KEY, regionService.getCountries());	
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(getStudentFilterPack());
		return STUDENT_VIEW_JSP;		
	}
	
	private StudentFilterPack getStudentFilterPack () {
		StudentFilterPack a = new StudentFilterPack();
	    StudentFilter[] filters = new StudentFilter[1]; 
	    filters[0] = new EqualStudentFilter("course");
		a.setArray(filters);
		return a;
	}
	
	@RequestMapping(value = EXCEL_STUDENT_LIST_REQUEST, method = RequestMethod.GET)
	public ModelAndView getExcelStudentList () {
		
		HashMap<String, Object> map = new HashMap<String, Object>(1);		
		map.put(StudentListExcelBuilder.SHEET_SOURCE_LIST_NAME, service.getStudents());				
		return new ModelAndView("excel-students", map);
	}
	
	@RequestMapping(value = PDF_STUDENT_REQUEST, method = RequestMethod.GET)
	public ModelAndView getPDFStudent (@RequestParam("uid") String uid) {
		
		HashMap<String, Object> map = new HashMap<String, Object>(1);		
		map.put(StudentPortfolioPDFBuilder.STUDENT_KEY, service.getStudent(uid));				
		return new ModelAndView("pdf-diplomas", map);
	}
	
}
