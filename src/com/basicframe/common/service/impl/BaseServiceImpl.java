package com.basicframe.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.ToolBox;

public class BaseServiceImpl<T> implements IBaseService<T> {
	
	private Log logger = LogFactory.getLog(getClass());

	private SqlMapper<T> sqlMapper;
	
	@Resource
	public LogGPUtil logGPUtil;
	
	
	public Log getLogger() {
		return logger;
	}
	
	public SqlMapper<T> getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper<T> sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	public List<T> queryPageList(Map<String, Object> map) {
		return sqlMapper.selectPageList(map);
	}

	public int queryTotalRows(Map<String, Object> map) {
		return sqlMapper.selectTotalRows(map);
	}
	
	public T queryById(int id) throws BusException {
		return sqlMapper.selectById(id);
	}
	
	public T queryById(String id) throws BusException {
		return sqlMapper.selectById(id);
	}
	
	public List<T> queryByMap(Map<String, Object> map) {
		return sqlMapper.selectByMap(map);
	}
	
	public T queryByName(String name) {
		return sqlMapper.selectByName(name);
	}
	
	public List<T> queryList(int id) {
		return sqlMapper.selectList(id);
	}
	
	public List<T> queryAll() {
		return sqlMapper.selectAll();
	}
	
	public List<T> queryAll(Map<String, Object> map){
		return sqlMapper.selectAll(map);
	}
	
	public void create(T vo, OperatorLog log) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(vo);
		try {
			//创建
			sqlMapper.insert(vo);
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void create(T vo) throws BusException {
		sqlMapper.insert(vo);
	}
	
	public void modify(T vo, OperatorLog log) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(vo);
		try {
			//修改
			if(sqlMapper.update(vo) < 1){
				throw new BusException(BusException.EDIT_FAILURE);
			}
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void modify(T vo) throws BusException {
		if(sqlMapper.update(vo) < 1){
			throw new BusException(BusException.EDIT_FAILURE);
		}
	}

	public void remove(int id, OperatorLog log) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(id);
		try {
			//删除
			if(sqlMapper.delete(id) < 1){
				throw new BusException(BusException.DELETE_FAILURE);
			}
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void remove(String id, OperatorLog log) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(id);
		try {
			//删除
			if(sqlMapper.delete(id) < 1){
				throw new BusException(BusException.DELETE_FAILURE);
			}
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void remove(int id) throws BusException {
		if(sqlMapper.delete(id) < 1){
			throw new BusException(BusException.DELETE_FAILURE);
		}
	}
	
	public void remove(String id) {
		sqlMapper.delete(id);
	}
	
	public void remove(Map<String, Object> map) {
		sqlMapper.delete(map);
	}
	
	public void remove(String[] ids) throws BusException {
		if(sqlMapper.delete(ids) < 1){
			throw new BusException(BusException.DELETE_FAILURE);
		}
	}

	public void remove(List<?> list) throws BusException {
		if(sqlMapper.delete(list) < 1){
			throw new BusException(BusException.DELETE_FAILURE);
		}
	}
	

	public void batchCreate(List<T> list, Class<? extends SqlMapper<T>> mapper) {
		// TODO Auto-generated method stub
	}

	public void batchModify(List<T> list, Class<? extends SqlMapper<T>> mapper) {
		// TODO Auto-generated method stub
	}

	public void batchRemove(int[] ids, Class<? extends SqlMapper<T>> mapper) {
		// TODO Auto-generated method stub
	}

}
