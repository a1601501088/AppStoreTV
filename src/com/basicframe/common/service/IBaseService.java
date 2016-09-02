package com.basicframe.common.service;

import java.util.List;
import java.util.Map;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.common.exception.BusException;
import com.basicframe.sys.model.OperatorLog;
/**
 * <p>Description: 公共Service接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IBaseService<T> {
	
	/**
     * 根据条件查询分页数据的总数
     * 
     * @param map 
     * 			分页条件参数
     * @return 总条数
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
	public int queryTotalRows(Map<String, Object> map);
	
	/**
     * 根据条件查询分页数据
     * 
     * @param map 
     * 			分页条件参数
     * @return 分页数据List
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
	public List<T> queryPageList(Map<String, Object> map);
	
	/**
	 * 根据ID查询实体对象
	 * 
	 * @param id 
	 * 			ID
	 * @return 查询出来的实体对象
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public T queryById(int id) throws BusException ;
	
	/**
	 * 根据ID查询实体对象
	 * 
	 * @param id 
	 * 			ID
	 * @return 查询出来的实体对象
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public T queryById(String id) throws BusException ;
	
	/**
	 * 根据ID查询实体对象
	 * 
	 * @param map 
	 * 			
	 * @return 查询出来的实体对象
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public List<T> queryByMap(Map<String, Object> map);
	
	/**
	 * 根据Name查询实体对象
	 * 
	 * @param name 
	 * 			名称
	 * @return 查询出来的实体对象
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public T queryByName(String name);
	
	/**
     * 根据ID查询数据列表
     * 
     * @param id 
     * 			ID
     * @return 数据List
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
	public List<T> queryList(int id);
	
	/**
     * 查询所有数据
     * 
     * @return 数据List
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
	public List<T> queryAll();
	
	/**
     * 查询所有数据
     * 
     * @return 数据List
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
	public List<T> queryAll(Map<String, Object> map);
	
	
	/**
	 * 批量插入数据
	 * 
	 * @param list
	 * 				需要插入的vo列表
	 * @param mapper
	 * 				数据操作mapper
	 * @author 唐颖杰
	 * @date： 2011-8-25
	 * @modify：
	 */
	public void batchCreate(List<T> list, Class<? extends SqlMapper<T>> mapper);
	
	/**
	 * 批量更新数据
	 * @param list
	 * 				需要更新的vo列表
	 * @param mapper
	 * 				数据操作mapper
	 * @author 唐颖杰
	 * @date： 2011-8-25
	 * @modify：
	 */
	public void batchModify(List<T> list, Class<? extends SqlMapper<T>> mapper);
	
	
	/**
	 * 批量删除数据
	 * 
	 * @param ids
	 * 				需要删除ids
	 * @param mapper
	 * 				数据操作mapper
	 * @author 唐颖杰
	 * @date： 2011-8-25
	 * @modify：
	 */
	public void batchRemove(int ids[], Class<? extends SqlMapper<T>> mapper);
	
	/**
	 * 根据实体对象创建数据
	 * 
	 * @param vo
	 * 			实体对象
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
     * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void create(T vo, OperatorLog log) throws BusException;
	
	/**
	 * 根据实体对象创建数据
	 * 
	 * @param vo
	 * 			实体对象
	 * @throws BusException
	 * 			业务异常
     * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void create(T vo) throws BusException;
	
	/**
	 * 根据实体对象更新数据
	 * 
	 * @param vo
	 * 			实体对象
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
     * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void modify(T vo, OperatorLog log) throws BusException;
	
	/**
	 * 根据实体对象更新数据
	 * 
	 * @param vo
	 * 			实体对象
	 * @throws BusException
	 * 			业务异常
     * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void modify(T vo) throws BusException;
	
	/**
	 * 删除
	 *
	 * @param id
	 * 			ID
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(int id, OperatorLog log) throws BusException;
	
	/**
	 * 删除
	 *
	 * @param id
	 * 			ID
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(String id, OperatorLog log) throws BusException;
	
	/**
	 * 删除
	 *
	 * @param id
	 * 			ID
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(int id) throws BusException;
	
	/**
	 * 删除
	 *
	 * @param id
	 * 			ID
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(String id);
	
	/**
	 * 删除
	 *
	 * @param map
	 * 			
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(Map<String, Object> map);
	
	/**
	 * 删除
	 *
	 * @param ids
	 * 			IDs
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(String[] ids) throws BusException;
	
	
	/**
	 * 删除
	 *
	 * @param list
	 * 			ID
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-20
	 * @modify
	 */
	public void remove(List<?> list) throws BusException;


}
