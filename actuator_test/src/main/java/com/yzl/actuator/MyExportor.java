package com.yzl.actuator;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.CounterWriter;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.stereotype.Component;

/**
 * Created by yinzuolong on 2017/4/5.
 */
@ExportMetricWriter
@Component
public class MyExportor implements GaugeWriter, CounterWriter {


    @Override
    public void increment(Delta<?> delta) {
        System.out.println(delta.getTimestamp() + ":" + delta.getName() + ":" + delta.getValue());
    }

    @Override
    public void reset(String s) {

    }

    @Override
    public void set(Metric<?> metric) {
        System.out.println(metric.getTimestamp() + ":" + metric.getName() + ":" + metric.getValue());
    }
}
