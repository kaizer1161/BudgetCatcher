package com.budgetcatcher.www.budgetcatcher.Network;

/**
 * API interface for price check.
 *
 * @author Mir Rayan
 * @since june-2018
 */

import com.budgetcatcher.www.budgetcatcher.Model.AddCategory;
import com.budgetcatcher.www.budgetcatcher.Model.AddOutstandingCheckBody;
import com.budgetcatcher.www.budgetcatcher.Model.CobrandLoginBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertAllowanceBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertBillBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertExpensesBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertIncomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyAllowanceBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyBillBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyCategory;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyExpenseBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyHomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyIncomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyOutstandingCheck;
import com.budgetcatcher.www.budgetcatcher.Model.SignUpBody;
import com.budgetcatcher.www.budgetcatcher.Model.User;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeCreateManualAccountBody;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeUserLoginBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Call<String> signIn(@Url String url);

    @GET
    Call<String> pieChart(@Url String url);

    @GET
    Call<String> getCatcher(@Url String url);

    @GET
    Call<String> getWeeksOrMonths(@Url String url);

    @GET
    Call<String> getCurrentWeeksOrMonths(@Url String url);

    @GET
    Call<String> getBill(@Url String url);

    @GET
    Call<String> getBudgetStatus(@Url String url);

    @GET
    Call<String> getHome(@Url String url);

    @GET
    Call<String> getIncome(@Url String url);

    @GET
    Call<String> getAllowance(@Url String url);

    @GET
    Call<String> getExpenses(@Url String url);

    @GET
    Call<String> getUserInfo(@Url String url);

    @GET
    Call<String> getBarChart(@Url String url);

    @GET()
    Call<String> getCategory(@Url String url);

    @GET()
    Call<String> getOutstandingChecks(@Url String url);

    @GET
    Call<String> yodleeGetAccount(@Url String url, @HeaderMap Map<String, String> headers);

    @GET
    Call<String> yodleeFastLink(@Url String url, @HeaderMap Map<String, String> headers);

    @POST(URL.signUp)
    Call<String> signUp(@HeaderMap Map<String, String> headers, @Body SignUpBody signUpBody);

    @POST()
    Call<String> cobrandLogin(@Url String url, @HeaderMap Map<String, String> headers, @Body CobrandLoginBody cobrandLoginBody);

    @POST()
    Call<String> yodleeCreateAccountManually(@Url String url, @HeaderMap Map<String, String> headers, @Body YodleeCreateManualAccountBody createManualAccountBody);

    @POST()
    Call<String> yodleeUserLogin(@Url String url, @HeaderMap Map<String, String> headers, @Body YodleeUserLoginBody yodleeUserLoginBody);

    @POST(URL.addCategory)
    Call<String> addCategory(@HeaderMap Map<String, String> headers, @Body AddCategory addCategory);

    @POST
    Call<String> profileSetup(@Url String url, @HeaderMap Map<String, String> headers, @Body String body /*ProfileSetupBody profileSetupBody*/);

    @POST
    Call<String> profileUpdate(@Url String url, @HeaderMap Map<String, String> headers, @Body User user);

    @POST(URL.insertBill)
    Call<String> insertBill(@HeaderMap Map<String, String> headers, @Body InsertBillBody insertBillBody);

    @POST(URL.insertIncome)
    Call<String> insertIncome(@HeaderMap Map<String, String> headers, @Body InsertIncomeBody insertIncomeBody);

    @POST(URL.insertExpense)
    Call<String> insertExpense(@HeaderMap Map<String, String> headers, @Body InsertExpensesBody insertExpensesBody);

    @POST(URL.insertAllowances)
    Call<String> insertAllowances(@HeaderMap Map<String, String> headers, @Body InsertAllowanceBody insertAllowanceBody);

    @POST(URL.addOC)
    Call<String> addOC(@HeaderMap Map<String, String> headers, @Body AddOutstandingCheckBody addOutstandingCheckBody);

    @PUT
    Call<String> modifyIncome(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyIncomeBody modifyIncomeBody);

    @PUT
    Call<String> modifyHome(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyHomeBody modifyHomeBody);

    @PUT
    Call<String> modifyBill(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyBillBody modifyBillBody);

    @PUT
    Call<String> modifyAllowance(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyAllowanceBody modifyAllowanceBody);

    @PUT
    Call<String> modifyExpense(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyExpenseBody modifyExpenseBody);

    @PUT
    Call<String> modifyCategory(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyCategory modifyCategory);

    @PUT
    Call<String> modifyOC(@Url String url, @HeaderMap Map<String, String> headers, @Body ModifyOutstandingCheck modifyOutstandingCheck);

    @PUT
    Call<String> updateDataTable(@Url String url, @HeaderMap Map<String, String> headers);

    @DELETE
    Call<String> deleteBill(@Url String url);

    @DELETE
    Call<String> deleteOC(@Url String url);

    @DELETE
    Call<String> deleteIncome(@Url String url);

    @DELETE
    Call<String> deleteAllowance(@Url String url);

    @DELETE
    Call<String> deleteExpense(@Url String url);

    @DELETE
    Call<String> deleteCategory(@Url String url);


}
