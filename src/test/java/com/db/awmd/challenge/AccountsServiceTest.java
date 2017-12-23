package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

	@Autowired
	private AccountsService accountsService;

	@Test
	public void addAccount() throws Exception {
		Account account = new Account("Id-123");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);
		Account account1 = new Account("Id-124");
		account1.setBalance(new BigDecimal(1001));
		this.accountsService.createAccount(account1);
		assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);

	}

	@Test
	public void transferAmount() throws InsufficientBalanceException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 3; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j < 4; j++) {
						try {
							accountsService.transferMoney("Id-124", "Id-123", 500 * j);
						} catch (InsufficientBalanceException e) {
							// TODO Auto-generated catch block
							assertThat(e).isInstanceOf(InsufficientBalanceException.class);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
		}
		executor.awaitTermination(5, TimeUnit.SECONDS);

	}

	@Test
	public void addAccount_failsOnDuplicateId() throws Exception {
		String uniqueId = "Id-" + System.currentTimeMillis();
		Account account = new Account(uniqueId);
		this.accountsService.createAccount(account);

		try {
			this.accountsService.createAccount(account);
			fail("Should have failed when adding duplicate account");
		} catch (DuplicateAccountIdException ex) {
			assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
		}

	}
}
