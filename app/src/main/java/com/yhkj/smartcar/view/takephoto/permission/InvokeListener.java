package com.yhkj.smartcar.view.takephoto.permission;


import com.yhkj.smartcar.view.takephoto.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
