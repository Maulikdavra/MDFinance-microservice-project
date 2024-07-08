package com.md.message.dto;

/**
 * <p>
 * @Author Maulik Davra
 * @CreatedOn 06/28/2024
 * @Since 1.0
 * </p>
 * </hr>
 *
 * AccountsMsgDto is a record class to hold the account details.
 * It is used to send the account details to the message queue.
 * The parameters defined in the record class are used to create the message.
 *
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
