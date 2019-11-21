package org.apache.synapse.core.axis2;

public interface Axis2SynapseEnvironmentMBean {

    public void setCoreThreadPoolSize(int size);

    public void setMaxThreadPoolSize(int size);

    public void setKeepAliveTime(long time);

    public int getCoreThreadPoolSize();

    public int getMaxThreadPoolSize();

    public long getKeepAliveTime();

}
