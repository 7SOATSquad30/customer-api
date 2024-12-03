package br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentQrCodeDTO {
    public String qrCodeData;
}
