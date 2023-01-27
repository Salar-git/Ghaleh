package ir.iamsalar.ghaleh.view.fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import ir.iamsalar.ghaleh.R
import ir.iamsalar.ghaleh.databinding.DialogLogInBinding
import ir.iamsalar.ghaleh.databinding.FragmentOnboardBinding
import ir.iamsalar.ghaleh.prefs
import ir.iamsalar.ghaleh.viewmodel.OnboardViewModel
import ir.iamsalar.ghaleh.viewmodel.OnboardViewModelFactory


class OnboardFragment : Fragment() {


    private var _binding: FragmentOnboardBinding? = null
    val binding get() = _binding!!

    private val viewModel: OnboardViewModel by viewModels {
        OnboardViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOnboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (prefs.getBoolean("login")) {
                next()
            }
            viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    prefs.setBoolean("login", true)
                    prefs.setString("token", it.token)
                    Snackbar.make(binding.root, "Login Successfull", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Continue") {
                            next()
                        }.setBackgroundTint(resources.getColor(R.color.green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                } else {
                    binding.btnLogin.isEnabled = true
                    binding.txtSignUp.isEnabled = true

                    Snackbar.make(binding.root, "Login Failed", Snackbar.LENGTH_INDEFINITE)
                        .setAction("TryAgain") {
                            openLoginDialog()
                        }.setBackgroundTint(resources.getColor(R.color.red))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
            })
            btnLogin.setOnClickListener {

                openLoginDialog()
            }

        }
    }

    private fun next() {
        findNavController().navigate(OnboardFragmentDirections.actionOnboardFragmentToCategorySelectionFragment())
    }


    private fun openLoginDialog() {

        var dialog = Dialog(requireActivity())


        val dialogBinding: DialogLogInBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                requireActivity()
            ), R.layout.dialog_log_in, null, false
        )
        dialog.setContentView(dialogBinding.root)

        dialog.window!!.setWindowAnimations(R.style.LoginDialogAnimation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialogBinding.apply {
            usernameTextField.editText?.doOnTextChanged { text, start, before, count ->
                usernameTextField.error = null
            }
            passwordTextField.editText?.doOnTextChanged { text, start, before, count ->
                passwordTextField.error = null
            }
            btnLogin.setOnClickListener {


                dialog.window!!.currentFocus?.hideKeyboard()
                dialog.window!!.currentFocus?.clearFocus()
                val checkResult = checkLoginInputs(
                    usernameTextField,
                    passwordTextField
                )
                if (checkResult) {
                    dialog.dismiss()
                }
            }
        }
        dialog.setOnDismissListener {

            binding.btnLogin.isEnabled = false
            binding.txtSignUp.isEnabled = false


        }
        dialog.show()
    }

    private fun checkLoginInputs(username: TextInputLayout, password: TextInputLayout): Boolean {
        val userNameValue = username.editText?.text.toString()
        val passwordValue = password.editText?.text.toString()
        if (userNameValue.isNullOrEmpty()) {
            username.error = "Usernsdame feild is empty"
            return false
        } else if (passwordValue.isNullOrEmpty()) {
            password.error = "Password feild is empty"
            return false
        }
        viewModel.login(userNameValue, passwordValue)
        return true

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}