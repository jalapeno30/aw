/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: ResponseFactory.java
 */
package com.ilts.anywhere.response;

import org.springframework.http.ResponseEntity;

import com.ilts.anywhere.response.http.AcceptedHttpResponse;
import com.ilts.anywhere.response.http.BadRequestHttpResponse;
import com.ilts.anywhere.response.http.CreatedHttpResponse;
import com.ilts.anywhere.response.http.ForbiddenHttpResponse;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import com.ilts.anywhere.response.http.MovedPermanentlyHttpResponse;
import com.ilts.anywhere.response.http.NotAllowedHttpResponse;
import com.ilts.anywhere.response.http.NotFoundHttpResponse;
import com.ilts.anywhere.response.http.OkHttpResponse;
import com.ilts.anywhere.response.http.ResetContentHttpResponse;
import com.ilts.anywhere.response.http.UnauthorizedHttpResponse;

public class ResponseFactory {
    public static Response makeResponse(ResponseType responseType) {
        Response response = null;

        switch (responseType) {
            case ERROR:
                response = new ErrorResponse();
                break;
            case SUCCESSBETPLACE:
                response = new PlaceBetResponse();
                break;
            case SUCCESSSAVETICKET:
                response = new SaveTicketInfoResponse();
                break;
            case SUCCESSCANCELBET:
                response = new CancelBetResponse();
                break ;
            case SUCCESSREGISTER:
                response = new RegisterResponse();
                break;
            case SUCCESSLOGIN:
                response = new LoginResponse();
                break;
            case SUCCESSSENDPASSWORD:
                response = new ForgotPasswordResponse();
                break;
            case SUCCESSNEWORDER:
                response = new NewOrderResponse();
                break;
            case CONFIRMFUNDINGPAYPAL:
                response = new PaypalConfirmFundingResponse();
                break;
            case SUCCESSCHANGESTATUS:
                response = new ChangeStatusResponse();
                break;
            case SUCCESSCHANGEROLE:
                response = new ChangeRoleResponse();
                break;
            case SUCCESSVALIDSESSION:
                response = new ValidSessionResponse();
                break;
            case ERRORINVALIDSESSION:
                response = new InvalidSessionErrorResponse();
                break;
            case SUCCESSDATA:
                response = new DataResponse();
                break;
            default:
                break;
        }

        return response;
    }

    public static HttpResponse makeHttpResponse(HttpResponseType responseType, Object data) {
        HttpResponse httpResponse;

        switch (responseType) {
            case OK:
                httpResponse = new OkHttpResponse(data);
                break;
            case CREATED:
                httpResponse = new CreatedHttpResponse(data);
                break;
            case ACCEPTED:
                httpResponse = new AcceptedHttpResponse(data);
                break;
            case RESETCONTENT:
                httpResponse = new ResetContentHttpResponse(data);
                break;
            case MOVEDPERMANENTLY:
                httpResponse = new MovedPermanentlyHttpResponse(data);
                break;
            case BADREQUEST:
                httpResponse = new BadRequestHttpResponse(data);
                break;
            case UNAUTHORIZED:
                httpResponse = new UnauthorizedHttpResponse(data);
                break;
            case FORBIDDEN:
                httpResponse = new ForbiddenHttpResponse(data);
                break;
            case NOTFOUND:
                httpResponse = new NotFoundHttpResponse(data);
                break;
            case NOTALLOWED:
                httpResponse = new NotAllowedHttpResponse(data);
                break;
            default:
                httpResponse = null;
        }

        return httpResponse;
    }

    public static HttpResponse makeHttpResponse(HttpResponseType responseType) {
        return makeHttpResponse(responseType, null);
    }
}