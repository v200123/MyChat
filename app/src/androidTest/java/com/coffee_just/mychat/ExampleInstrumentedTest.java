package com.coffee_just.mychat;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.coffee_just.mychat.utils.MyToast;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.coffee_just.mychat", appContext.getPackageName());
    }

    @Test
    public void useOkSocket(){
        ConnectionInfo info = new ConnectionInfo("104.238.184.237", 8080);
        IConnectionManager manager  = OkSocket.open(info);
        Context appContext = InstrumentationRegistry.getTargetContext();
        manager.registerReceiver(new SocketActionAdapter() {
                                     @Override
                                     public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                                         MyToast.OutToast(appContext,"连接成功").show();
                                     }
                                 });
                manager.connect();
    }
}
