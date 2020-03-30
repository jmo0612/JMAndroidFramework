package com.thowo.jmandroidframework.component;

import java.io.File;

public interface JMAnFilePickerListener {
    void onPicked(File chosen);
    void onCancel();
}
