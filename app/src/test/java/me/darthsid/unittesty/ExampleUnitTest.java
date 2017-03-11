package me.darthsid.unittesty;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import me.darthsid.unittesty.exceptions.LoginFailedException;
import me.darthsid.unittesty.utils.LoginModule;
import me.darthsid.unittesty.utils.Multiplier;
import me.darthsid.unittesty.utils.NetworkManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void multiplier_fiveAndThree_isFifteen() {
        Multiplier multiplier = new Multiplier();
        assertThat(multiplier.multiply(5, 3), is(15));
//        assertEquals(multiplier.multiply(5,3), 15);
    }


    @Mock
    NetworkManager fakeNetworkManager;

    @Before
    public void resetMocks() {
        reset(fakeNetworkManager);
    }

    @Test
    public void login_correctCredentials_returnsToken() {
        final String TOKEN = "token1";

        // setup
        when(fakeNetworkManager.post("/login", "Sid abcd")).thenReturn(TOKEN);
        LoginModule loginModule = new LoginModule(fakeNetworkManager);

        String returnToken = loginModule.login("Sid", "abcd");

        assertThat(returnToken, is(TOKEN));
    }

    @Test(expected = LoginFailedException.class)
    public void should_throwException_when_loginWithFalseCredentials() {
        // setup
        when(fakeNetworkManager.post(
                eq("/login"),
                argThat(not("Sid abcd"))
        )).thenReturn("false");
        when(fakeNetworkManager.post("/login", "Sid abcd")).thenReturn("token1");
        LoginModule loginModule = new LoginModule(fakeNetworkManager);

        String returnToken = loginModule.login("Sid", "abcde");
        System.out.println(returnToken);
    }

    @Test(expected = LoginFailedException.class)
    public void should_throwException_when_networkCallFailed() {
        //noinspection unchecked
        when(fakeNetworkManager.post(
                Matchers.any(String.class),
                Matchers.any(String.class)
        )).thenThrow(RuntimeException.class);
        LoginModule loginModule = new LoginModule(fakeNetworkManager);

        String returnToken = loginModule.login("Sid", "abcde");
    }
}