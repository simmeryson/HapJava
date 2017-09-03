package com.guok.event;

import java.util.List;

/**
 * @author guok
 */

public interface Event {

    String getTarget();

    String getDomain();

    List<ParameterBean> getValues();
}
