package com.cdut.myschool.service.okHttpTool;

public class CdutHolder {

    private CdutHolder(){};

    public CdutHolder getInstance() {
        return CdutInstance.holder;
    }

    private ThreadLocal<CdutEduSys> holder = new ThreadLocal<>();

    public void put(CdutEduSys cdutEduSys) {
        holder.set(cdutEduSys);
    }

    public CdutEduSys get() {
        return holder.get();
    }

    public void remove() {
        holder.remove();
    }

    private static class CdutInstance{
        static CdutHolder holder = new CdutHolder();
    }
}
