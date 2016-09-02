package com.basicframe.common.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;
/**
 * <p>Description: 数据库操作公用SqlMapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface SqlMapper<T> {
	
	/**
     * 根据条件查询分页数据的总数
     * 
     * @param map 
     * 			分页条件参数
     * @return 总条数
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public int selectTotalRows(Map<String, Object> map) throws DataAccessException;
    
    /**
     * 根据条件查询分页数据
     * 
     * @param map 
     * 			分页条件参数
     * @return 分页数据List
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public List<T> selectPageList(Map<String, Object> map) throws DataAccessException;
    
    /**
	 * 根据ID查询实体对象
	 * 
	 * @param id 
	 * 			数据库主键ID
	 * @return 查询出来的实体对象
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public T selectById(int id) throws DataAccessException;
    
    /**
	 * 根据ID查询实体对象
	 * 
	 * @param id 
	 * 			数据库主键ID
	 * @return 查询出来的实体对象
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public T selectById(String id) throws DataAccessException;
    
    /**
	 * 根据ID查询实体对象
	 * 
	 * @param map 
	 * 			map
	 * @return 查询出来的实体对象
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public List<T> selectByMap(Map<String, Object> map) throws DataAccessException;
    
    /**
     * 根据名称查询实体对象
     * 
     * @param name 
     * 			名称
     * @return 查询出来的实体对象
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public T selectByName(String name) throws DataAccessException;
    
    /**
	 * 根据ID查询实体对象
	 * 
	 * @param vo 
	 * 			实体对象
	 * @return 查询出来的实体对象
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public T selectByBean(T vo) throws DataAccessException;
    
    /**
     * 查询数据List
     * 
     * @param id 
     * 			ID
     * @return 数据List
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public List<T> selectList(int id) throws DataAccessException;
    
    /**
     * 查询数据List
     * 
     * @param map 
     * 			查询条件
     * @return 数据List
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public List<T> selectList(Map<String, Object> map) throws DataAccessException;
    
    /**
     * 查询所有
     * 
     * @return 数据List
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public List<T> selectAll() throws DataAccessException;
    
    /**
     * 查询所有
     * 
     * @return 数据List
     * @throws DataAccessException
     * @author 唐颖杰
     * @date： 2011-8-20
     * @modify：
     */
    public List<T> selectAll(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 保存实体对象
	 * 
	 * @param vo 
	 * 			实体对象
	 * @return 受影响的行数
	 * @throws DataAccessException
	* @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public int insert(T vo) throws DataAccessException;
	
	/**
	 * 更新实体对象
	 * 
	 * @param vo 
	 * 			实体对象
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int update(T vo) throws DataAccessException;
    
    /**
	 * 删除实体对象
	 * 
	 * @param id 
	 * 			数据库主键ID
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int delete(int id) throws DataAccessException;
    
    /**
	 * 删除实体对象
	 * 
	 * @param id 
	 * 			数据库主键ID
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int delete(String id) throws DataAccessException;
    
    /**
	 * 删除实体对象
	 * 
	 * @param map
	 * 			数据库主键ID
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int delete(Map<String, Object> map) throws DataAccessException;
    
    /**
	 * 删除实体对象
	 * 
	 * @param ids 
	 * 			数据库主键IDS
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int delete(String[] ids) throws DataAccessException;
    
    /**
	 * 删除实体对象
	 * 
	 * @param list 
	 * 			数据库主键ID List
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
    public int delete(List<?> list) throws DataAccessException;
    
}
