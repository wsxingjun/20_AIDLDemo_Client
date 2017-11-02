package oztaking.com.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import oztaking.com.service.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private IMyAidlInterface myService;
    private MyConn myConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_CallRomoteService = (Button) findViewById(R.id.bt_CallRomoteService);
        bt_CallRomoteService.setOnClickListener(this);

        Intent intent = new Intent();
        intent.setAction("com.oztaking.call");
        //Intent intent = new Intent();
        myConn = new MyConn();
        bindService(intent, myConn,BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        try {
            myService.CallRemoteServiceMethod();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        unbindService(myConn);
        super.onDestroy();
    }

    public class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //【1】关键一步：获取中间人对象
            myService = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
