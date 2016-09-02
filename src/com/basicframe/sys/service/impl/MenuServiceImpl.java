package com.basicframe.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.MenuMapper;
import com.basicframe.sys.dao.PermissionsMapper;
import com.basicframe.sys.dao.RoleMenuMapper;
import com.basicframe.sys.dao.RolePermissionsMapper;
import com.basicframe.sys.model.Menu;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.service.IMenuService;
import com.basicframe.utils.ToolBox;

@Service("menuServ")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
	
	@Resource
	private MenuMapper menuMapper;
	@Resource
	private RoleMenuMapper roleMenuMapper;
	@Resource
	private PermissionsMapper permissionsMapper;
	@Resource
	private RolePermissionsMapper rolePermissionsMapper;
	
	@Resource
	public void setMapper(MenuMapper menuMapper){
		super.setSqlMapper(menuMapper);
	}
	
	public void create(Menu menu, OperatorLog log) throws BusException {
		//检测对象是否为空
		ToolBox.isNull(menu);
		ToolBox.isNull(menu.getMenuName());
		try {
			Menu m = menuMapper.selectByName(menu.getMenuName());
			if(m != null){
				throw new BusException(BusException.MENU_NAME_IS_EXISTS);
			}
			//创建菜单
			menuMapper.insert(menu);
			//新值
			log.setOperatorValue("父菜单ID:" + menu.getParentId() + ";名称:" + menu.getMenuName() + ";是否使用:" + menu.getIsDisabled());
			//操作动作
			log.setOperatorAction("创建菜单");
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void modify(Menu menu, OperatorLog log) throws BusException {
		//检测对象是否为空
		ToolBox.isNull(menu);
		ToolBox.isNull(menu.getMenuId());
		ToolBox.isNull(menu.getMenuName());
		try {
			Menu m = menuMapper.selectByName(menu.getMenuName());
			//查询要修改的菜单
			Menu md = menuMapper.selectById(menu.getMenuId());
			if(m != null){
				if(m.getMenuId() != md.getMenuId()){
					throw new BusException(BusException.MENU_NAME_IS_EXISTS);
				}
			}
			//更新对象
			md.setMenuName(ToolBox.isCorrect(menu.getMenuName()) ? menu.getMenuName() : md.getMenuName());
			md.setMenuPic(ToolBox.isCorrect(menu.getMenuPic()) ? menu.getMenuPic() : md.getMenuPic());
			md.setMenuUrl(ToolBox.isCorrect(menu.getMenuUrl()) ? menu.getMenuUrl() : md.getMenuUrl());
			md.setMenuOrder(menu.getMenuOrder() != 0 ? menu.getMenuOrder() : md.getMenuOrder());
			md.setIsDisabled(menu.getIsDisabled());
			md.setIsModify(menu.getIsModify());
			md.setIsOpen(menu.getIsOpen());
			md.setIsRemove(menu.getIsRemove());
			//修改菜单
			menuMapper.update(md);
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void remove(int menuId, OperatorLog log) throws BusException {
		//检测对象是否为空
		ToolBox.isNull(menuId);
		try {
			List<Menu> list = menuMapper.selectByParentId(menuId);
			if(list != null && list.size() > 0){
				throw new BusException(BusException.MENU_WAS_QUOTED);
			}
			//删除角色菜单权限
			roleMenuMapper.deleteByMenuId(menuId);
			//删除角色菜单操作权限
			rolePermissionsMapper.deleteByMenuId(menuId);
			//删除菜单权限
			permissionsMapper.deleteByMenuId(menuId);
			//删除菜单
			menuMapper.delete(menuId);
			//操作动作
			log.setOperatorAction("删除菜单");
			log.setOperatorValue("菜单ID:"+menuId);
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	
	public List<Menu> queryByParentId(int parentId) {
		return menuMapper.selectByParentId(parentId);
	}
	
	public List<Menu> queryUserMenu(int userId) {
		return menuMapper.selectUserMenu(userId);
	}

	public List<Menu> queryRoleMenu(int roleId) {
		return menuMapper.selectRoleMenu(roleId);
	}
	
	public List<Menu> queryPerMenu() {
		return menuMapper.selectPerMenu();
	}


}
