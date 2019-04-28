package com.example.alipay;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.example.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2019042464258546";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088121911681480";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "pay@qsoa.cn";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnxfZLk71wV328ii7X+IxRUqgwW3ASp9HOnqK6HZXl5oXwe55SefrauN6E91Y6gynZw+zSvUHnm8srLqmhqsXiBBT9DeX+sf5Afa+Exb8IMZOS1PbHy8+qk2E+OCP0iXTw5U95MXKh2wYi8mdRAedpK0CYGDlqytH3hMU/msisuocWO9EIPVt44vIHqkU5KQPWv1uI6/Ifg/lX+ohJgSV6H00Bhf1dvJDBMGLX4XqHKAIM4QBxXGFlxIFAj69uEEDLpJ1YaIy1/tbOyCEogx22eAmUM7tl/uA8HqQ9DTRtijCg6EsNCONJwVaMGcjkiS/yWpAKsThMXX4ebyWN5kwzAgMBAAECggEAaKOckNitf6Lh5jTdMJBxm8sM4VQ2WzxWLCNe5OvM8R3NDgdb4J75qW9CQfYtzsK92HdNfsbRkw3lMqKg5Bcp4mSfXpFLkIYdSMXbMdlP42FgZGT1IfnpJL9VGERVkJAm4pRy8+i2UWLUufksHSct2LdES1hYf1XOjS0/BQOaA7gzhrUgnpz54BEbLIzctr5tPBWGP9gzeVg/tagQalEhe9GxP+3sYHMd1FzQ1y3umQa/hz69SXrMIwVwEZPHRaYA5jccD5GDHlZh586MWOAKWHf+yrID0I75wbg4G+9bjkCxQ8itwsquZiQe+nSkELUQ30AfPTWfBREhN3mOKRHwYQKBgQDfdD1zCQ/RoOitwnWCS9oJ9ECCLUqIUsJAuirktJzTtvQtouI1c1OkPHj0qiihDg7GIlPhNrYRppoUM3+UkZ9XMydSX3O0N8w2qmwq0BfSkNC8EY4MB5ururEYSZWwuk+EGwSWRQJP/MkyH9oMQ7Swxc8lXB6ebnwQXUEsYiakkQKBgQDANZiI+aAJFrU4zhCcGU+6UYI1z1r4qvtr+6anSw3Rum4y5NEvjBE3E48VOBHpJLCjdl75D59RcfVKR125IwrHQrLnAsRXOcPeg5dtsL5lV44RSYx188R/sEGptjsJL5EhoCNXUNG9U5RsDi5fXrrFYVXfDXbV8xlC9kdY1ZG2gwKBgQCrDjfQLPqwFcao5XjBEsHJ5vHO9U7eBsRvelRFmcGIDMDlgpA6LJu12Rka6nuJttI2eKbPe+tqnafLmRSsLcHVpfIPC8TuC9zHaDS/nWbvKS6xGZssuFJB5vuUxRfTHvyp+2jKg+9ePfvi5CEuTFIxFGwbhGZoD9nnSQwQJt7AEQKBgD8BkqXcBizUh8U+L02sMmlmDKPs++olylMEZAXU4YvNbBA816GZbY87Vxzii2R8hXaf99P23gfJrlmR+VQV+5OD9rcpPglFw0IuqrXnSRJZWBWsoWGM7e3Zbjzeoo2JTS7TmqIluQsx2aZuI59ZoynOlWKPvFO346ECsDKbl69XAoGBAKRiXbk5wzB0p2sAqtqcfd6PgLk9qKydRo71pCvZ5ePVUwEoHlfjjuTtHf359pvfLL+HQxK8k3HGhRR/hTB6BSnTzKDKHETx2LJTpWviUNPM471Zcj3wmdlnZcNnlQ5RPoNEegLszl/hOHchWBYUmbg2dlLbLAdX58Q3f63F3v8X";

    public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnxfZLk71wV328ii7X+IxRUqgwW3ASp9HOnqK6HZXl5oXwe55SefrauN6E91Y6gynZw+zSvUHnm8srLqmhqsXiBBT9DeX+sf5Afa+Exb8IMZOS1PbHy8+qk2E+OCP0iXTw5U95MXKh2wYi8mdRAedpK0CYGDlqytH3hMU/msisuocWO9EIPVt44vIHqkU5KQPWv1uI6/Ifg/lX+ohJgSV6H00Bhf1dvJDBMGLX4XqHKAIM4QBxXGFlxIFAj69uEEDLpJ1YaIy1/tbOyCEogx22eAmUM7tl/uA8HqQ9DTRtijCg6EsNCONJwVaMGcjkiS/yWpAKsThMXX4ebyWN5kwzAgMBAAECggEAaKOckNitf6Lh5jTdMJBxm8sM4VQ2WzxWLCNe5OvM8R3NDgdb4J75qW9CQfYtzsK92HdNfsbRkw3lMqKg5Bcp4mSfXpFLkIYdSMXbMdlP42FgZGT1IfnpJL9VGERVkJAm4pRy8+i2UWLUufksHSct2LdES1hYf1XOjS0/BQOaA7gzhrUgnpz54BEbLIzctr5tPBWGP9gzeVg/tagQalEhe9GxP+3sYHMd1FzQ1y3umQa/hz69SXrMIwVwEZPHRaYA5jccD5GDHlZh586MWOAKWHf+yrID0I75wbg4G+9bjkCxQ8itwsquZiQe+nSkELUQ30AfPTWfBREhN3mOKRHwYQKBgQDfdD1zCQ/RoOitwnWCS9oJ9ECCLUqIUsJAuirktJzTtvQtouI1c1OkPHj0qiihDg7GIlPhNrYRppoUM3+UkZ9XMydSX3O0N8w2qmwq0BfSkNC8EY4MB5ururEYSZWwuk+EGwSWRQJP/MkyH9oMQ7Swxc8lXB6ebnwQXUEsYiakkQKBgQDANZiI+aAJFrU4zhCcGU+6UYI1z1r4qvtr+6anSw3Rum4y5NEvjBE3E48VOBHpJLCjdl75D59RcfVKR125IwrHQrLnAsRXOcPeg5dtsL5lV44RSYx188R/sEGptjsJL5EhoCNXUNG9U5RsDi5fXrrFYVXfDXbV8xlC9kdY1ZG2gwKBgQCrDjfQLPqwFcao5XjBEsHJ5vHO9U7eBsRvelRFmcGIDMDlgpA6LJu12Rka6nuJttI2eKbPe+tqnafLmRSsLcHVpfIPC8TuC9zHaDS/nWbvKS6xGZssuFJB5vuUxRfTHvyp+2jKg+9ePfvi5CEuTFIxFGwbhGZoD9nnSQwQJt7AEQKBgD8BkqXcBizUh8U+L02sMmlmDKPs++olylMEZAXU4YvNbBA816GZbY87Vxzii2R8hXaf99P23gfJrlmR+VQV+5OD9rcpPglFw0IuqrXnSRJZWBWsoWGM7e3Zbjzeoo2JTS7TmqIluQsx2aZuI59ZoynOlWKPvFO346ECsDKbl69XAoGBAKRiXbk5wzB0p2sAqtqcfd6PgLk9qKydRo71pCvZ5ePVUwEoHlfjjuTtHf359pvfLL+HQxK8k3HGhRR/hTB6BSnTzKDKHETx2LJTpWviUNPM471Zcj3wmdlnZcNnlQ5RPoNEegLszl/hOHchWBYUmbg2dlLbLAdX58Q3f63F3v8X";


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(MainActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(MainActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MainActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(MainActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
