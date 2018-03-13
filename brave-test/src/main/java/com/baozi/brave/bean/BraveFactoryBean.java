package com.baozi.brave.bean;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.http.HttpSpanCollector;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by baozi on 2018/2/27.
 */
@Service
public class BraveFactoryBean implements FactoryBean<Brave> {

    private static final Logger LOGGER = Logger.getLogger(BraveFactoryBean.class.getName());

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * zipkin服务器ip及端口，不配置默认localhost:9411
     */
    private String zipkinHost = "http://localhost:9411/";

    /**
     * 采样率 0~1 之间
     */
    private float rate = 1.0f;

    /**
     * 单例
     */
    private Brave instance;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getZipkinHost() {
        return zipkinHost;
    }

    public String getRate() {
        return String.valueOf(rate);
    }

    public void setRate(String rate) {
        this.rate = Float.parseFloat(rate);
    }

    public void setZipkinHost(String zipkinHost) {
        this.zipkinHost = zipkinHost;
    }

    private void createInstance() {
        Brave.Builder builder;
        if (this.serviceName == null) {
            builder = new Brave.Builder();
        } else {
            builder = new Brave.Builder(this.serviceName);
        }
        builder.spanCollector(HttpSpanCollector.create(this.zipkinHost, new EmptySpanCollectorMetricsHandler())).traceSampler(Sampler.create(rate)).build();
        LOGGER.info("brave dubbo config collect whith httpSpanColler , rate is " + rate);
        this.instance = builder.build();
    }

    @Override
    public Brave getObject() {
        if (this.instance == null) {
            this.createInstance();
        }
        return this.instance;
    }

    @Override
    public Class<?> getObjectType() {
        return Brave.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
