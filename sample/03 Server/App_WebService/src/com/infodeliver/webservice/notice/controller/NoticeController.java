package com.infodeliver.webservice.notice.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infodeliver.webservice.common.controller.AbstractBaseController;
import com.infodeliver.webservice.common.dto.RequestParameters;
import com.infodeliver.webservice.common.dto.ResponseResults;
import com.infodeliver.webservice.notice.service.INoticeService;

@Controller
public class NoticeController extends AbstractBaseController {

	@Autowired
	private INoticeService noticeService;

	@RequestMapping(value = "/saveNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults saveNotice(@RequestBody RequestParameters params) {

		int categoryId = 0;
		String subject = "";
		String text = "";
		int importance = 0;
		String sendDate = "";
		int replyFlag = 0;
		String createId = "";
		String recipientArray = "";
		int recipientType = 0;
		int groupId = 0;

		logger.debug("category_id:" + params.getParameter("category_id"));
		logger.debug("subject:" + params.getParameter("subject"));
		logger.debug("text:" + params.getParameter("text"));
		logger.debug("importance:" + params.getParameter("importance"));
		logger.debug("send_date:" + params.getParameter("send_date"));
		logger.debug("reply_flag:" + params.getParameter("reply_flag"));
		logger.debug("create_id:" + params.getParameter("create_id"));
		logger.debug("recipient_type:" + params.getParameter("recipient_type"));
		logger.debug("recipient_array:"
				+ params.getParameter("recipient_array"));
		logger.debug("group_id:" + params.getParameter("group_id"));

		categoryId = Integer.parseInt(params.getParameter("category_id"));
		subject = params.getParameter("subject");
		text = params.getParameter("text");
		importance = Integer.parseInt(params.getParameter("importance"));
		sendDate = params.getParameter("send_date");
		replyFlag = Integer.parseInt(params.getParameter("reply_flag"));
		createId = params.getParameter("create_id");
		recipientType = Integer.parseInt(params.getParameter("recipient_type"));

		recipientArray = params.getParameter("recipient_array");
		groupId = Integer.parseInt(params.getParameter("group_id"));

		// save notice info
		long insertNoticeId = noticeService
				.saveNotice(groupId, categoryId, subject, text, importance, sendDate,
						replyFlag, createId, recipientArray, recipientType);

		logger.debug("insertNoticeId:"
				+ insertNoticeId);
		
		ResponseResults results = new ResponseResults(params);
		if (insertNoticeId == -1) {
			//error
//			results.putResult("back_code", insertNoticeId);
		} else {
			//id
			results.putResult("insert_id", insertNoticeId);
		}

		return results;
	}

	@RequestMapping(value = "/editNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults editNotice(@RequestBody RequestParameters params) {

		int noticeId = -1;
		int categoryId = 0;
		String subject = "";
		String text = "";
		int importance = 0;
		String sendDate = "";
		int replyFlag = 0;
		String createId = "";
		String recipientArray = "";
		int recipientType = 0;
		int groupId = 0;

		logger.debug("notice_id:" + params.getParameter("notice_id"));
		logger.debug("category_id:" + params.getParameter("category_id"));
		logger.debug("subject:" + params.getParameter("subject"));
		logger.debug("text:" + params.getParameter("text"));
		logger.debug("importance:" + params.getParameter("importance"));
		logger.debug("send_date:" + params.getParameter("send_date"));
		logger.debug("reply_flag:" + params.getParameter("reply_flag"));
		logger.debug("create_id:" + params.getParameter("create_id"));
		logger.debug("recipient_type:" + params.getParameter("recipient_type"));

		logger.debug("recipient_array:"
				+ params.getParameter("recipient_array"));
		logger.debug("group_id:" + params.getParameter("group_id"));

		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		categoryId = Integer.parseInt(params.getParameter("category_id"));
		subject = params.getParameter("subject");
		text = params.getParameter("text");
		importance = Integer.parseInt(params.getParameter("importance"));
		sendDate = params.getParameter("send_date");
		replyFlag = Integer.parseInt(params.getParameter("reply_flag"));
		createId = params.getParameter("create_id");
		recipientType = Integer.parseInt(params.getParameter("recipient_type"));

		recipientArray = params.getParameter("recipient_array");
		groupId = Integer.parseInt(params.getParameter("group_id"));

		// save notice info
		long insertNoticeId = noticeService
				.editNotice(noticeId,groupId, categoryId, subject, text, importance, sendDate,
						replyFlag, createId, recipientArray, recipientType);

		logger.debug("insertNoticeId:"
				+ insertNoticeId);
		
		ResponseResults results = new ResponseResults(params);
		if (insertNoticeId == -1) {
			//error
//			results.putResult("back_code", insertNoticeId);
		} else {
			//id
			results.putResult("insert_id", insertNoticeId);
		}

		return results;
	}
	
	@RequestMapping(value = "/getNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getNotice(@RequestBody RequestParameters params) {

		int startNo = 0;
		int max = 0;
		String userId = "";

		logger.debug("start_no:" + params.getParameter("start_no"));
		logger.debug("max:" + params.getParameter("max"));
		logger.debug("userId:" + params.getParameter("user_id"));

		startNo = Integer.parseInt(params.getParameter("start_no"));
		max = Integer.parseInt(params.getParameter("max"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = noticeService.getNotice(startNo, max ,userId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("notices", list);
		return results;
	}

	@RequestMapping(value = "/getMoreNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getMoreNotice(@RequestBody RequestParameters params) {

		int startId = 0;
		int startNo = 0;
		int max = 0;
		String userId = "";

		logger.debug("start_id:" + params.getParameter("start_id"));
		logger.debug("start_no:" + params.getParameter("start_no"));
		logger.debug("max:" + params.getParameter("max"));
		logger.debug("userId:" + params.getParameter("user_id"));

		startId = Integer.parseInt(params.getParameter("start_id"));
		startNo = Integer.parseInt(params.getParameter("start_no"));
		max = Integer.parseInt(params.getParameter("max"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = noticeService.getMoreNotice(startId,
				startNo, max, userId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("notices", list);
		return results;
	}

	@RequestMapping(value = "/getNewNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getNewNotice(@RequestBody RequestParameters params) {

		int startId = 0;
		int startNo = 0;
		int max = 0;
		String userId = "";

		logger.debug("start_id:" + params.getParameter("start_id"));
		logger.debug("start_no:" + params.getParameter("start_no"));
		logger.debug("max:" + params.getParameter("max"));
		logger.debug("userId:" + params.getParameter("user_id"));

		startId = Integer.parseInt(params.getParameter("start_id"));
		startNo = Integer.parseInt(params.getParameter("start_no"));
		max = Integer.parseInt(params.getParameter("max"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = noticeService.getNewNotice(startId,
				startNo, max, userId);

		ResponseResults results = new ResponseResults(params);
		if (!list.isEmpty()) {
			logger.debug("count:" + list.size());
		}
		results.putResult("notices", list);
		return results;
	}
	
	@RequestMapping(value = "/updateRecipientStatus", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults updateRecipientStatus(@RequestBody RequestParameters params) {

		int noticeId = 0;
		String userId = "";
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		int updateCode = noticeService.updateRecipientStatus(noticeId,userId);
		ResponseResults results = new ResponseResults(params);
		results.putResult("update_code", updateCode);
		return results;
	}
	
	@RequestMapping(value = "/delNotice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults delNotice(@RequestBody RequestParameters params) {

		int noticeId = 0;
		String userId = "";
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		int updateCode = noticeService.delNotice(noticeId,userId);
		ResponseResults results = new ResponseResults(params);
		results.putResult("update_code", updateCode);
		return results;
	}
	
	
	@RequestMapping(value = "/getNoticeWithRecipientStatus", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getNoticeWithRecipientStatus(@RequestBody RequestParameters params) {

		int noticeId = 0;
		String userId = "";
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");

		List<Map<String, Object>> list = noticeService.getNoticeWithRecipientStatus(noticeId, userId);
		
		ResponseResults results = new ResponseResults(params);
		results.putResult("notice_bean", list);
		return results;
	}

	@RequestMapping(value = "/getNoticeWithRecipientStatusUpdate", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResults getNoticeWithRecipientStatusUpdate(@RequestBody RequestParameters params) {

		int noticeId = 0;
		String userId = "";
		boolean needChangeFlag = false;
		logger.debug("noticeId:" + params.getParameter("notice_id"));
		noticeId = Integer.parseInt(params.getParameter("notice_id"));
		logger.debug("userId:" + params.getParameter("user_id"));
		userId = params.getParameter("user_id");
		logger.debug("needChangeFlag:" + params.getParameter("need_change_recipient_status"));
		needChangeFlag = Boolean.parseBoolean(params.getParameter("need_change_recipient_status"));

		List<Map<String, Object>> list = noticeService.getNoticeWithRecipientStatusUpdate(noticeId, userId,needChangeFlag);
		
		ResponseResults results = new ResponseResults(params);
		results.putResult("notice_bean", list);
		return results;
	}
	
    @RequestMapping(value = "/notify_getday", method = RequestMethod.POST)	
    public @ResponseBody ResponseResults getDayDuanxin(@RequestBody RequestParameters params)	
    {	
    	//lixiaoui phone->user_name
        logger.debug("phone:"+params.getParameter("dunxin_day"));	
        	
        //logger.debug("password:"+params.getParameter("password"));	
        	
        String strSelYmd = params.getParameter("dunxin_day");	
       	
        List<Map<String, Object>> resultlist = noticeService.getDayDuanxin(strSelYmd,"123");	
        	
        //boolean resultState = true;	
        ResponseResults results = new ResponseResults(params);	
        	
        JSONObject json = new JSONObject();	
        JSONArray jsonArray = new JSONArray(); 	
        for (Map<String, Object> m : resultlist) {	
        	JSONObject item = new JSONObject();  
            for (String k : m.keySet()) {	
            	item.put(k,  m.get(k));
            }	
            jsonArray.add(item);	
        }	
        results.putResult("list", jsonArray);	
        //results.putResult("is_login", resultState);	
        return results;	
    }	
    	
    	
    @RequestMapping(value = "/notify_getIcons", method = RequestMethod.POST)	
    public @ResponseBody ResponseResults getIcons(@RequestBody RequestParameters params)	
    {	
    	//lixiaoui phone->user_name
        logger.debug("phone:"+params.getParameter("dunxin_day"));	
        	
        //logger.debug("password:"+params.getParameter("password"));	
        	
        String strSelYmd = params.getParameter("dunxin_day");	
       	
        List<Map<String, Object>> resultlist = noticeService.getIcons(strSelYmd,"123");	
        	
        //boolean resultState = true;	
        ResponseResults results = new ResponseResults(params);	
        	
        JSONObject json = new JSONObject();	
        JSONArray jsonArray = new JSONArray(); 	
        for (Map<String, Object> m : resultlist) {	
        	JSONObject item = new JSONObject();  
            for (String k : m.keySet()) {	
            	item.put(k,  m.get(k));
            }	
            jsonArray.add(item);	
        }	
        results.putResult("list", jsonArray);	
        //results.putResult("is_login", resultState);	
        return results;	
    }	

}
