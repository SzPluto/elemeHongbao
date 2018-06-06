package com.eleme.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eleme.dao.RecordDao;
import com.eleme.service.StatService;

@Service("statService")
public class StatServiceImpl implements StatService{
	
	@Resource
	private RecordDao recordDao;

	@Override
	public String todayGetRecordNumber() {
		String record = recordDao.todayGetRecordNumber();
		if(record == null){
			return "0";
		}else{
			return record;
		}
		
	}

	@Override
	public String todayGetRecordMoney() {
		String record = recordDao.todayGetRecordMoney();
		if(record == null){
			return "0";
		}else{
			return record;
		}
	}

	@Override
	public String allGetRecordNumber() {
		String record = recordDao.allGetRecordNumber();
		if(record == null){
			return "0";
		}else{
			return record;
		}
	}

	@Override
	public String allGetRecordMoney() {
		String record = recordDao.allGetRecordMoney();
		if(record == null){
			return "0";
		}else{
			return record;
		}
	}
	
}