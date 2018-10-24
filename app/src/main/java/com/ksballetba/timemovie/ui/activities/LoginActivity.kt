package com.ksballetba.timemovie.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.fragments.SignInFragment
import com.ksballetba.timemovie.ui.fragments.SignUpFragment
import com.ksballetba.timemovie.ui.widgets.CardPageTransformer
import kotlinx.android.synthetic.main.activity_login.*
import com.google.gson.Gson
import com.ksballetba.timemovie.mvp.model.bean.QQLoginBean
import com.ksballetba.timemovie.mvp.model.bean.QQUserinfoBean
import com.ksballetba.timemovie.ui.activities.LoginActivity.Companion.APP_ID
import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.connect.common.Constants
import com.tencent.open.utils.HttpUtils
import com.tencent.tauth.IRequestListener
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.SocketTimeoutException


class LoginActivity : AppCompatActivity() {

    companion object {
        const val APP_ID = "101510659"
    }
    lateinit var signInFragment: SignInFragment
    lateinit var signUpFragment: SignUpFragment
    var fragmentList = ArrayList<Fragment>()
    lateinit var mTencent:Tencent
    lateinit var mTencentUiListener: BaseUiListener
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mTencent = Tencent.createInstance(APP_ID,applicationContext)
        mTencentUiListener = BaseUiListener()
        initFragments()
    }
    private fun initFragments() {
        setSupportActionBar(login_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        signInFragment = SignInFragment()
        signUpFragment = SignUpFragment()
        fragmentList.add(signInFragment)
        fragmentList.add(signUpFragment)
        login_viewpager.offscreenPageLimit = 2
        login_viewpager.adapter = KotlinPagerAdapter(fragmentList,supportFragmentManager)
        login_viewpager.setPageTransformer(true,CardPageTransformer())
        login_qq.setOnClickListener {
            tencentLogin()
        }
    }

    fun showSignUp() {
       login_viewpager.currentItem = 1
    }

    fun showSignIn() {
        login_viewpager.currentItem=0
    }

    fun tencentLogin(){
        if(!mTencent.isSessionValid){
            mTencent.login(this,"all",object :IUiListener{
                override fun onComplete(response: Any?) {
                    val qq = Gson().fromJson<QQLoginBean>(response.toString(),QQLoginBean::class.java)
                    val token = QQToken(APP_ID)
                    token.setAccessToken(qq.accessToken,qq.expiresIn.toString())
                    token.openId = qq.openid
                    val info = UserInfo(this@LoginActivity,token)
                    info.getUserInfo(object :IUiListener{
                        override fun onComplete(response: Any?) {
                            val qqUserInfo = Gson().fromJson<QQUserinfoBean>(response.toString(),QQUserinfoBean::class.java)
                            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                            val editor = sharedPreferences.edit()
                            editor.putString("qq_user_avater",qqUserInfo.figureurlQq2)
                            editor.putString("qq_user_nickname",qqUserInfo.nickname)
                            editor.apply()
                            finish()
                        }

                        override fun onCancel() {

                        }

                        override fun onError(p0: UiError?) {
                            Log.d("debug",p0.toString())
                        }
                    })
                }

                override fun onCancel() {

                }

                override fun onError(e: UiError?) {
                    Log.d("debug","onError:${e?.errorCode}${e?.errorMessage}")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode,resultCode,data,mTencentUiListener)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

class BaseUiListener:IUiListener{
    override fun onComplete(response: Any?) {
        Log.d("debug","onConplete:$response")
    }

    override fun onCancel() {

    }

    override fun onError(e: UiError?) {
        Log.d("debug","onError:${e?.errorCode}${e?.errorMessage}")
    }
}



