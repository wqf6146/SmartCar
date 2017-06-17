package com.yhkj.smartcar.event;

import com.vise.xsnow.event.IEvent;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/5/26.
 */

public class JumpFragmentEvent implements IEvent {
    SupportFragment fragment;
    public JumpFragmentEvent(SupportFragment fragment){
       this.fragment = fragment;
    }

    public SupportFragment getFragment() {
        return fragment;
    }
}
