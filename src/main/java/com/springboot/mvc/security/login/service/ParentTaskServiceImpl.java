package com.springboot.mvc.security.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.repository.ParentTaskRepo;

@Service
public class ParentTaskServiceImpl implements ParentTaskService {
	
	@Autowired
	private ParentTaskRepo parentTaskRepo ;
	@Override
	public ParentTask findParentTaskByid(long parentid) {
		Optional<ParentTask> parentTaskOpt;
		parentTaskOpt = parentTaskRepo.findById(parentid);
		if (parentTaskOpt.isPresent()) {
			return parentTaskOpt.get();
		}else {
			return null;
		}
	}

	@Override
	public ParentTask saveParentTask(ParentTask parentTask) {
		return parentTaskRepo.save(parentTask);
	}

	@Override
	public boolean deleteParentTask(long parentid) {
		if (parentTaskRepo.findById(parentid).isPresent()) {
			parentTaskRepo.deleteById(parentid);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<ParentTask> findAllParentTask() {
		return parentTaskRepo.findAll();
	}

}
