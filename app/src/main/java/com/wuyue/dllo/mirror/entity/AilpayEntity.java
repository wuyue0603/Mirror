package com.wuyue.dllo.mirror.entity;

/**
 * Created by dllo on 16/4/18.
 */
public class AilpayEntity {

    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"1460963151hld\"&subject=\"KAREN WALKER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"1538.00\"&body=\"KAREN WALKER\"&it_b_pay =\"30m\"&sign=\"Uwh3xr8hDpE3vYAUemvHy5P96W6Gjh4spnbhFkaz5s4x%2ByumEwclXPw0CoYblIC4KAJiAA2U8MDAVX7%2FC5z3vOntjdhYEF1Y3LtyLWDEVSbtqOEzg2ylWwHQupPfBSBvKiU2OZVNZxYjAruPDjaWqWhzkwNt50pLwJnSqw3kvzo%3D\"&sign_type=\"RSA\""}
     */

    private String msg;
    /**
     * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"&notify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="1460963151hld"&subject="KAREN WALKER"&payment_type="1"&seller_id="2088021758262531"&total_fee="1538.00"&body="KAREN WALKER"&it_b_pay ="30m"&sign="Uwh3xr8hDpE3vYAUemvHy5P96W6Gjh4spnbhFkaz5s4x%2ByumEwclXPw0CoYblIC4KAJiAA2U8MDAVX7%2FC5z3vOntjdhYEF1Y3LtyLWDEVSbtqOEzg2ylWwHQupPfBSBvKiU2OZVNZxYjAruPDjaWqWhzkwNt50pLwJnSqw3kvzo%3D"&sign_type="RSA"
     */

    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String str;

        public void setStr(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
