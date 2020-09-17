package com.peony.web;

import com.peony.common.entity.AuditLog;
import com.peony.common.entity.Organization;
import com.peony.common.service.AuditLogService;
import com.peony.common.service.OrganizationService;
import com.peony.common.web.helper.UserHelper;
import com.peony.data.entity.TicketsPO;
import com.peony.data.entity.UserPO;
import com.peony.data.repository.TicketsRepository;
import com.peony.data.repository.UserRepository;
import com.peony.signature.util.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author hk
 * @date 2019/8/1
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudSignatureApplication.class)
public class TemplateWebApplicationTest {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationService organizationService;

    @Test
    public void testPass() {
        log.info(userHelper.encodePassword("123456"));
    }

    @Test
    public void testQrCode() {
        BufferedImage image = QrCodeUtil.createQrCode("www.baidu.com");
        System.out.println(image);
    }

    @Test
    public void test1() {
        AuditLog auditLog = AuditLog.builder()
//                .objectId(String.valueOf(target.getId()))
                .objectId("123123")
                //.objectType(table.name())
                // .type(auditLogType)
                // .userId(Optional.ofNullable(target.getLastModifiedBy()).map(e->e.getId()).orElse(null))
                // .createdDate(target.getLastModifiedDate())
                .id(123123)
                .build();

        auditLogService.save(auditLog);
    }

    @Test
    public void test2() {
        UserPO userPO = UserPO.builder().name("111").build();
        userPO.setId(2);
        userRepository.save(userPO);
    }

    @Test
    public void test3() {
        Organization organization = Organization.builder().build();

//        organization.setId(4);
        organization.setCreatedById(2);
        organizationService.save(organization);
    }

    @Autowired
    private TicketsRepository ticketsRepository;

    @Test
    public void testPass11() {
        List<TicketsPO> all = ticketsRepository.findAll();
        all.get(2).getSignatures().get(0);

        log.info("123456");

    }

    @Test
    public void testPass12() {

    }

}