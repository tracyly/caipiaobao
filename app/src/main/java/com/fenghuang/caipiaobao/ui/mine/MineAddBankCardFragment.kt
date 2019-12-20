package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.text.TextUtils
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineBankList
import com.fenghuang.caipiaobao.widget.dialog.WheelViewDialog
import com.fenghuang.caipiaobao.widget.dialog.city.CityEntity
import com.fenghuang.caipiaobao.widget.dialog.city.CitySelectDialog
import kotlinx.android.synthetic.main.dialog_city.*
import kotlinx.android.synthetic.main.dialog_wheel_view.*
import kotlinx.android.synthetic.main.dialog_wheel_view.tvWheelSure
import kotlinx.android.synthetic.main.fragment_mine_add_bank_card.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 15:45
 * @ Describe 添加银卡
 *
 */

class MineAddBankCardFragment : BaseMvpFragment<MineAddBankCardPresenter>() {

    private var province: String? = null
    private var city: String? = null
    var bankListAll: List<MineBankList>? = null
    var bankCode: String = "-1001"
    var dataList: List<MineBankList>? = null


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineAddBankCardPresenter()

    override fun getContentResID() = R.layout.fragment_mine_add_bank_card

    override fun getPageTitle() = getString(R.string.mine_add_bank_card)

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false

    val list = arrayListOf<String>()

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initData() {
        mPresenter.getBankList()
    }

    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        tvOpenCity.setOnClickListener {
            val citySelectDialog = CitySelectDialog(getPageActivity())
            citySelectDialog.tvWheelSure.setOnClickListener {
                tvOpenCity.text = (citySelectDialog.cityPickerView.opt1SelectedData as CityEntity).name +
                        " " + (citySelectDialog.cityPickerView.opt2SelectedData as CityEntity).name +
                        " " + (citySelectDialog.cityPickerView.opt3SelectedData as CityEntity).name
                province = (citySelectDialog.cityPickerView.opt1SelectedData as CityEntity).name
                city = (citySelectDialog.cityPickerView.opt2SelectedData as CityEntity).name
                citySelectDialog.dismiss()
            }
            citySelectDialog.show()
        }

        bindSubmit.setOnClickListener {
            if (bankCode == "-1001") {
                ToastUtils.showWarning("请选择银行卡")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tvOpenCity.text)) {
                ToastUtils.showWarning("请选择开户城市")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etOpenCityOther.text)) {
                ToastUtils.showWarning("请填写开户支行")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etOpenName.text)) {
                ToastUtils.showWarning("请填写开户姓名")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etOpenNumber.text)) {
                ToastUtils.showWarning("请填写银行卡号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etOpenPassWord.text)) {
                ToastUtils.showWarning("请填写支付密码")
                return@setOnClickListener
            }
            if (etOpenNumber.text.length < 15 || etOpenNumber.text.length > 19) {
                ToastUtils.showWarning("请填写正确的16-19位银行卡号")
                return@setOnClickListener
            }
            mPresenter.bindBankCard(bankCode,
                    province.toString(),
                    city.toString(),
                    etOpenCityOther.text.toString(),
                    etOpenName.text.toString(),
                    etOpenNumber.text.toString(), etOpenPassWord.text.toString())
        }
    }

    fun initWheelView(bankList: List<String>) {
        linPickLayout.setOnClickListener {
            val wheelViewDialog = WheelViewDialog(getPageActivity(), bankList, "请选择银行卡")
            wheelViewDialog.tvWheelSure.setOnClickListener {
                tvUserBankCard.text = wheelViewDialog.wheelView.selectedItemData.toString()
                bankCode = bankListAll?.get(wheelViewDialog.wheelView.selectedItemPosition)?.code.toString()
                wheelViewDialog.dismiss()
            }
            wheelViewDialog.show()
        }
    }

}
