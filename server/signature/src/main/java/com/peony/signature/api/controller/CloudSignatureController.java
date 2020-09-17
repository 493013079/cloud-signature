package com.peony.signature.api.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Signature;
import com.peony.common.entity.Tickets;
import com.peony.common.service.SignatureService;
import com.peony.common.service.TicketsService;
import com.peony.common.web.RestResult;
import com.peony.data.repository.SignatureRepository;
import com.peony.signature.api.vo.SignatureVO;
import com.peony.signature.api.vo.TicketsVO;
import com.peony.signature.util.QrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author 陈浩
 * @date 2020/9/8
 */

@Api(value = "云签名相关接口", tags = {"云签名相关接口"})
@ApiSort(100)
@RestController
@RequestMapping("/signature")
@RequiredArgsConstructor
@Slf4j
public class CloudSignatureController {

    private final TicketsService ticketsService;

    private final SignatureService signatureService;

    private final SignatureRepository signatureRepository;

    /**
     * 传入待签名对象 targetUrl 可以是 pdf、jpg、doc、html 等，生成签名票据 ticketId
     * 请求参数为url，生成签名票据 ticketId，然后保存在系统中
     */
    @ApiOperationSupport(author = "", order = 10)
    @ApiOperation("传入待签名对象")
    @PostMapping("/tickets")
    public RestResult<String> saveTicket(@Validated @RequestParam String targetURL) {

        Tickets tickets = Tickets.builder()
                .targetURL(targetURL)
                .build();
        Tickets saved = ticketsService.save(tickets);
        return RestResult.successData(saved.getId());
    }

    /**
     * 获取签名二维码，提供给领导扫码进入签名页面
     */
    @ApiOperationSupport(author = "", order = 20)
    @ApiOperation("获取签名二维码")
    @GetMapping("/tickets/{id}/qrcode")
    public ResponseEntity<Resource> getQrCode(@PathVariable("id") Tickets tickets) throws IOException {

        String targetURL = tickets.getTargetURL();

        BufferedImage image = QrCodeUtil.createQrCode(targetURL);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=2592000");
        headers.add("charset", "utf-8");
        headers.add("Content-Type", "image/png");
        Resource resource = new InputStreamResource(bufferedImageToInputStream(image));

        return ResponseEntity.ok().headers(headers).contentType(MediaType.valueOf("image/jpeg")).body(resource);
    }

    /**
     * 获取签名票据信息
     * 有签名签名数据则返回targetURL和签名数据，没有签名数据则只返回targetURL
     */
    @ApiOperationSupport(author = "", order = 30)
    @ApiOperation("获取签名票据信息")
    @GetMapping("/tickets/{id}")
    public RestResult<TicketsVO> getTicket(@PathVariable("id") Tickets tickets) {

        List<String> values = tickets.getSignatures().stream().map(Signature::getValue).collect(toList());
        TicketsVO ticketsVO = TicketsVO.builder()
                .targetURL(tickets.getTargetURL())
                .signatureValues(values)
                .build();
        return RestResult.successData(ticketsVO);
    }

    /**
     * 提交签名数据
     */
    @ApiOperationSupport(author = "", order = 40)
    @ApiOperation("提交签名数据")
    @PostMapping("/tickets/{id}/signature")
    public RestResult<Object> saveSignature(@PathVariable("id") Tickets tickets,
                                            @RequestBody SignatureVO signatureVO) {

        Signature signature = Signature.builder()
                .strokePath(signatureVO.getStrokePath())
                .value(signatureVO.getValue())
                .ticketId(tickets.getId())
                .build();
        signatureService.save(signature);

        return RestResult.SUCCESS;
    }

    /**
     * 删除签名数据
     */
    @ApiOperationSupport(author = "", order = 50)
    @ApiOperation("删除签名数据")
    @DeleteMapping("/tickets/{signatureId}/delete")
    public RestResult<Object> deleteSignature(@PathVariable("signatureId") Signature signature) {

        signatureService.delete(signature);
        return RestResult.SUCCESS;
    }

    /**
     * 将BufferedImage转换为InputStream
     */
    private InputStream bufferedImageToInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    /**
     * 测试缓存
     */
    @ApiOperationSupport(author = "", order = 60)
    @ApiOperation("测试缓存查询")
    @GetMapping("/tickets/cacheTest/{id}")
    public RestResult<Object> findByCache(@PathVariable("id") String id) {
        Tickets tickets = ticketsService.findById(id);
        return RestResult.successData(tickets);
    }

}
