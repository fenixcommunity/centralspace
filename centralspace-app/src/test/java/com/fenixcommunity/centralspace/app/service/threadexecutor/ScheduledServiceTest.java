package com.fenixcommunity.centralspace.app.service.threadexecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fenixcommunity.centralspace.app.service.appstatus.AppStatusService;
import com.fenixcommunity.centralspace.domain.model.memory.SessionAppInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduledServiceTest {

    @InjectMocks
    private ScheduledService scheduledService;

    @Mock
    private AppStatusService appStatusService;

    @BeforeEach
    public void before() {
        when(appStatusService.createSessionAppInfo(anyString())).thenReturn(new SessionAppInfo(1L, "runnableMethod"));
    }

    @Test
    void scheduleSecondsTest() throws InterruptedException {
        scheduledService.delayInSeconds(this::runnableMethod, 5);
        Thread.sleep(6000);
        verify(appStatusService, times(1)).createSessionAppInfo(any());
    }

    @Test
    void repeatSecondsTest() throws InterruptedException {
        scheduledService.repeatInSeconds(this::runnableMethod, 5);
        Thread.sleep(6000);
        verify(appStatusService, times(2)).createSessionAppInfo(any());
    }

    private void runnableMethod() {
        SessionAppInfo sessionAppInfo = appStatusService.createSessionAppInfo("runnableMethod");
        System.out.println(sessionAppInfo.getSomeInfo());
    }
}