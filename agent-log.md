# logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  <statusListener class="ch.qos.logback.core.status.NopStatusListener" />

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <charset>UTF-8</charset>
      <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} | %-20thread | %highlight(%-5level) | %-40logger{36} | %msg %n</pattern>
    </encoder>
  </appender>


  <appender name="JSON" class="ch.qos.logback.classic.sift.SiftingAppender">

    <discriminator>
      <key>filename</key>
      <defaultValue>debug</defaultValue>
    </discriminator>

    <sift>
      <appender name="MDC-${filename}" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${filename}.log</file>

        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
          <layout class="cambium.logback.json.FlatJsonLayout">
            <jsonFormatter class="ch.qos.logback.contrib.jackson.JacksonJsonFormatter">
                <prettyPrint>true</prettyPrint>
            </jsonFormatter>
            <timestampFormat>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</timestampFormat>
            <timestampFormatTimezoneId>UTC</timestampFormatTimezoneId>
            <appendLineSeparator>true</appendLineSeparator>
          </layout>
        </encoder>

        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
          <fileNamePattern>${filename}-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
          <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
            <maxFileSize>50MB</maxFileSize>
          </timeBasedFileNamingAndTriggeringPolicy>
          <maxHistory>30</maxHistory>
        </rollingPolicy>
      </appender>
    </sift>
  </appender>



  <root level="DEBUG">
    <appender-ref ref="STDOUT" />
    <appender-ref ref="JSON" />
  </root>


</configuration>

```

# 출력 결과

```json
{
  "timestamp" : "2021-04-02T03:07:11.736Z",
  "level" : "INFO",
  "recent_sale_count" : "3244",
  "mobile_discount_price" : "980",
  "is_new_item" : "false",
  "total_sale_count" : "14504",
  "npay" : "true",
  "created_at" : "\"2020-04-01T12:14:01Z\"",
  "best" : "false",
  "product_code" : "\"4874604973\"",
  "soldout" : "false",
  "mart_updated_at" : "\"2021-04-02T02:57:54Z\"",
  "style_name" : "\"부산대\"",
  "zzim" : "0",
  "channel_name" : "\"라빗\"",
  "view_count_from_window" : "76154",
  "store_id" : "\"100165553\"",
  "images" : "\"[\\\"http://shop1.phinf.naver.net/20210228_112/1614516505582ymF6B_JPEG/15652401295364357_1833438288.jpg\\\",\\\"http://shop1.phinf.naver.net/20200401_255/1585742784059iibss_JPEG/23104326681777288_1044574970.jpg\\\"]\"",
  "pc_discount_price" : "980",
  "popular_score" : "13017.05",
  "naver_category" : "\"발목양말\"",
  "content_text" : "\"250mm 발볼 넓으신 분께서는 남성용 추천 해드립니다. ★ 핑크 민트 신상컬러 추가 되었습니다. product INFO 소라 블랙 화이트 차콜 그레이 겨자 버건디 카키 네이비 카멜 브라운 진베이지 연베이지 오트밀 퍼플 형광오렌지 형광옐로우 형광핑크 - 사이즈는 재는 방법에 따라 1~5cm 오차가 있을 수 있습니다. 모든 각도와 해상도에 따라 제품색상이 왜곡 될 수 있는 점 참고 부탁드립니다. Fabric 면 스판덱스 폴리 - 혼방 표시의경우 거래처로 부터 혼용율 확인이 지연되고 있습니다. 정확한 표기가 어려우므로 임의로 기재 된 점 참고 부탁드립니다. MD comment 총17컬러의 발을 감싸주는 갑종양말 입니다. 골지로 되어 있구요. 신축성이 좋아서 발이 편한 상품 이랍니다. 운동화를 신어도 벗겨짐이 전혀 없어 매일 신고 싶어 지는 아이템 이예요. 국내생산으로 제작 된 양말 이라 질이 아주 만족스러운상품 이랍니다. 여성용 17컬러 남성용11컬러 준비 되어 있습니다. 맘에 드신다면 찜 눌러 주세요~! Fitting Cut 실리콘이 있는 상품 입니다. 운동화 에도 벗겨지지 않아요 :) Detail Cut 민트 핑크 신상 파스텔 컬러 입니다. 화이트 오트밀 카키 버건디 민트 핑크 겨자 카멜 진베이지 연베이지 브라운 블랙 네이비 그레이 차콜 형광핑크 형광연두 형광오렌지 ■ 공지사항 배송비 5만원이상 구매 시 무료배송 - 기본배송료 (3,000원) 단, 제주 및 도서상간 지역 추가금 발생 - 반품 (3,000원) - 교환 왕복배송비 (6,000원) - 출고 후, 택배 이동 확인은 대한통운 고객센터로 확인 시 더욱 빠르게 확인이 가능 하시답니다. - 배송은 기본 2~7일 정도 (공휴일,주말제외) 소요 됩니다. - 전화 상담은 어려울 수 있으니 고객문의나 톡톡으로 남겨 주심 빠르게 안내 도와드리겠습니다. - 영업시간 오후1시~5시 까지 이며 주말,공휴일은 휴무이며 다소 상담이 늦어 질 수 있습니다. 언제나 알차고 합리적인 가격으로 쇼핑을 즐길 수 있도록 노력 하겠습니다.\"",
  "stock_quantity" : "23301",
  "sale_price" : "1400",
  "product_no" : "\"4859487630\"",
  "tags" : "\"유닉크한느낌,유행을타지않는,착용감좋은양말,합리적인,스타일리시,모던유니크,믹스매치하기좋은,개성템,40대여성,30대여성\"",
  "filename" : "logstash",
  "exposure" : "true",
  "average_review_score" : "4.65",
  "name" : "\"여성 골지 벗겨지지 않는 갑종 덧신\"",
  "logger" : "agent.command",
  "message" : "stored!"
}
{
  "timestamp" : "2021-04-02T03:07:11.802Z",
  "level" : "INFO",
  "recent_sale_count" : "328",
  "mobile_discount_price" : "18800",
  "is_new_item" : "false",
  "total_sale_count" : "735",
  "npay" : "true",
  "created_at" : "\"2021-03-13T09:37:11Z\"",
  "best" : "false",
  "product_code" : "\"5452935667\"",
  "soldout" : "false",
  "mart_updated_at" : "\"2021-04-02T00:52:24Z\"",
  "style_name" : "\"금산\"",
  "zzim" : "0",
  "channel_name" : "\"모노타임\"",
  "view_count_from_window" : "69123",
  "store_id" : "\"1000003939\"",
  "images" : "\"[\\\"http://shop1.phinf.naver.net/20210313_9/1615622138650qVJtD_JPEG/16757973460656511_211250437.jpg\\\"]\"",
  "pc_discount_price" : "18800",
  "popular_score" : "11109.8",
  "naver_category" : "\"니트/스웨터\"",
  "content_text" : "\"심플한 디자인으로 코디하기 정말 좋은 니트 소개해요 전체 단가라 패턴으로 포인트 주어 밋밋함 없애주었구요 넥 라인 카라 디테일을 살려 단정한 무드도 느껴진답니다 착용했을 때 군더더기 없는 핏 감과 신축성, 스판감 정말 너무 좋아 슬림 66언니들까지 착용 가능할 것 같은 아이템이랍니다 베이직하고 심플한 디자인과 퀄리티 정말 좋은 소재, 탄탄한 짜임으로 데일리로 착용하기 정말 좋으니 하나쯤 소장하셔도 절대 후회 없는 아이템이랍니다 https://shopping.naver.com/style/style/stores/1000003939/products/5447397446 ▼챡용 라이브 다시 보기▼ (블랙)스트라이프, (그레이)스트라이프 2가지 컬러로 준비했어요 < Size info (cm)> ONE SIZE 총장 61 / 가슴 단면 55.5 / 소매 단면 18.5 / 소매 길이 52.5 / 어깨 54.5 측정 방법에 따라 1cm~3cm 오차 생길 수 있어요 모니터나 화면 설정에 따라 색상 차이가 있을 수 있으니 참고 부탁드려요 모노 타임의 모든 의류 세탁 시 드라이클리닝을 권장 드립니다 같은 사이즈라도 개인의 체형에 따라 착용 핏을 다 다를 수 있어요 상세 cm로 치수 적어 드릴 테니 가지고 계신 옷들과 비교 후 주문 부탁드릴게요 Detail cut ■ (블랙)스트라이프 ■ (그레이)스트라이프 NAVER REVIEW QUEEN ♥ 3만원 쿠폰 지급 ♥ 매달 '2,4째주' 금요일 베스트 리뷰를 선정하여 '리뷰 퀸'이 되신 고객님 총 3분에게 3만원 상당의 쿠폰을 드립니다. 리뷰 퀸은 착용했을 때의 리얼한 표현과 착용 핏이 잘 보여진 정성스러운 리뷰 중에 베스트 리뷰를 선정합니다. ※ 리뷰에 올라온 포토리뷰의 사진은 홈페이지에 노출될 수 있습니다 ※ ※ 오직 네이버에서 구매해 주신 분들만 해당됩니다 ※ 모노 타임에 게시된 모든 컨텐츠는 저작권법에 의거하여 보호받고 있으며, 모노 타임의 승인 없이 무단 도용 시 경고 없이 저작권법에 의해 법적 조치를 받을 수 있습니다 ※배송의 경우 당일 배송 불가, 2~7일 정도 ★인기 품목 상품, 리오더 상품인 경우 길게는 2주까지 소요될 수 있어요★ 이점 양해 부탁드릴게요. 수제화나 개인 오더 제품인 경우 1~2주 정도 소요될 수 있어요. 모노타임은 CJ 대한통운을 이용하고 있어요. ※교환 및 환불 경우 물건 하자 및 상품 불량은 택배비 착불이에요. 단순 변심은 택배비 본인 부담입니다. 주문 내역에서 반품 요청 후 수거 요청까지 해주세요. CJ 대한통운 택배가 아닌 다른 택배 이용하실 경우 선불로 보내주셔야 합니다. ※제품 문의 평일 : 오후 1시 ~ 오후 6시 주말, 공휴일 휴무 041-751-3637 / 041-753-3637 010-9707-3637\"",
  "stock_quantity" : "1257",
  "sale_price" : "30000",
  "product_no" : "\"5430303268\"",
  "tags" : "\"부드러운니트,오피스룩\"",
  "filename" : "logstash",
  "exposure" : "true",
  "average_review_score" : "4.16",
  "name" : "\"[봄신상 무배] 누리 카라 스트라이프 루즈핏 니트\"",
  "logger" : "agent.command",
  "message" : "stored!"
}
{
  "timestamp" : "2021-04-02T03:07:33.403Z",
  "level" : "INFO",
  "recent_sale_count" : "3244",
  "mobile_discount_price" : "980",
  "is_new_item" : "false",
  "total_sale_count" : "14504",
  "npay" : "true",
  "created_at" : "\"2020-04-01T12:14:01Z\"",
  "best" : "false",
  "product_code" : "\"4874604973\"",
  "soldout" : "false",
  "mart_updated_at" : "\"2021-04-02T02:57:54Z\"",
  "style_name" : "\"부산대\"",
  "zzim" : "0",
  "channel_name" : "\"라빗\"",
  "view_count_from_window" : "76154",
  "store_id" : "\"100165553\"",
  "images" : "\"[\\\"http://shop1.phinf.naver.net/20210228_112/1614516505582ymF6B_JPEG/15652401295364357_1833438288.jpg\\\",\\\"http://shop1.phinf.naver.net/20200401_255/1585742784059iibss_JPEG/23104326681777288_1044574970.jpg\\\"]\"",
  "pc_discount_price" : "980",
  "popular_score" : "13017.05",
  "naver_category" : "\"발목양말\"",
  "content_text" : "\"250mm 발볼 넓으신 분께서는 남성용 추천 해드립니다. ★ 핑크 민트 신상컬러 추가 되었습니다. product INFO 소라 블랙 화이트 차콜 그레이 겨자 버건디 카키 네이비 카멜 브라운 진베이지 연베이지 오트밀 퍼플 형광오렌지 형광옐로우 형광핑크 - 사이즈는 재는 방법에 따라 1~5cm 오차가 있을 수 있습니다. 모든 각도와 해상도에 따라 제품색상이 왜곡 될 수 있는 점 참고 부탁드립니다. Fabric 면 스판덱스 폴리 - 혼방 표시의경우 거래처로 부터 혼용율 확인이 지연되고 있습니다. 정확한 표기가 어려우므로 임의로 기재 된 점 참고 부탁드립니다. MD comment 총17컬러의 발을 감싸주는 갑종양말 입니다. 골지로 되어 있구요. 신축성이 좋아서 발이 편한 상품 이랍니다. 운동화를 신어도 벗겨짐이 전혀 없어 매일 신고 싶어 지는 아이템 이예요. 국내생산으로 제작 된 양말 이라 질이 아주 만족스러운상품 이랍니다. 여성용 17컬러 남성용11컬러 준비 되어 있습니다. 맘에 드신다면 찜 눌러 주세요~! Fitting Cut 실리콘이 있는 상품 입니다. 운동화 에도 벗겨지지 않아요 :) Detail Cut 민트 핑크 신상 파스텔 컬러 입니다. 화이트 오트밀 카키 버건디 민트 핑크 겨자 카멜 진베이지 연베이지 브라운 블랙 네이비 그레이 차콜 형광핑크 형광연두 형광오렌지 ■ 공지사항 배송비 5만원이상 구매 시 무료배송 - 기본배송료 (3,000원) 단, 제주 및 도서상간 지역 추가금 발생 - 반품 (3,000원) - 교환 왕복배송비 (6,000원) - 출고 후, 택배 이동 확인은 대한통운 고객센터로 확인 시 더욱 빠르게 확인이 가능 하시답니다. - 배송은 기본 2~7일 정도 (공휴일,주말제외) 소요 됩니다. - 전화 상담은 어려울 수 있으니 고객문의나 톡톡으로 남겨 주심 빠르게 안내 도와드리겠습니다. - 영업시간 오후1시~5시 까지 이며 주말,공휴일은 휴무이며 다소 상담이 늦어 질 수 있습니다. 언제나 알차고 합리적인 가격으로 쇼핑을 즐길 수 있도록 노력 하겠습니다.\"",
  "stock_quantity" : "23301",
  "sale_price" : "1400",
  "product_no" : "\"4859487630\"",
  "tags" : "\"유닉크한느낌,유행을타지않는,착용감좋은양말,합리적인,스타일리시,모던유니크,믹스매치하기좋은,개성템,40대여성,30대여성\"",
  "filename" : "logstash",
  "exposure" : "true",
  "average_review_score" : "4.65",
  "name" : "\"여성 골지 벗겨지지 않는 갑종 덧신\"",
  "logger" : "agent.command",
  "message" : "stored!"
}
{
  "timestamp" : "2021-04-02T03:07:33.444Z",
  "level" : "INFO",
  "recent_sale_count" : "328",
  "mobile_discount_price" : "18800",
  "is_new_item" : "false",
  "total_sale_count" : "735",
  "npay" : "true",
  "created_at" : "\"2021-03-13T09:37:11Z\"",
  "best" : "false",
  "product_code" : "\"5452935667\"",
  "soldout" : "false",
  "mart_updated_at" : "\"2021-04-02T00:52:24Z\"",
  "style_name" : "\"금산\"",
  "zzim" : "0",
  "channel_name" : "\"모노타임\"",
  "view_count_from_window" : "69123",
  "store_id" : "\"1000003939\"",
  "images" : "\"[\\\"http://shop1.phinf.naver.net/20210313_9/1615622138650qVJtD_JPEG/16757973460656511_211250437.jpg\\\"]\"",
  "pc_discount_price" : "18800",
  "popular_score" : "11109.8",
  "naver_category" : "\"니트/스웨터\"",
  "content_text" : "\"심플한 디자인으로 코디하기 정말 좋은 니트 소개해요 전체 단가라 패턴으로 포인트 주어 밋밋함 없애주었구요 넥 라인 카라 디테일을 살려 단정한 무드도 느껴진답니다 착용했을 때 군더더기 없는 핏 감과 신축성, 스판감 정말 너무 좋아 슬림 66언니들까지 착용 가능할 것 같은 아이템이랍니다 베이직하고 심플한 디자인과 퀄리티 정말 좋은 소재, 탄탄한 짜임으로 데일리로 착용하기 정말 좋으니 하나쯤 소장하셔도 절대 후회 없는 아이템이랍니다 https://shopping.naver.com/style/style/stores/1000003939/products/5447397446 ▼챡용 라이브 다시 보기▼ (블랙)스트라이프, (그레이)스트라이프 2가지 컬러로 준비했어요 < Size info (cm)> ONE SIZE 총장 61 / 가슴 단면 55.5 / 소매 단면 18.5 / 소매 길이 52.5 / 어깨 54.5 측정 방법에 따라 1cm~3cm 오차 생길 수 있어요 모니터나 화면 설정에 따라 색상 차이가 있을 수 있으니 참고 부탁드려요 모노 타임의 모든 의류 세탁 시 드라이클리닝을 권장 드립니다 같은 사이즈라도 개인의 체형에 따라 착용 핏을 다 다를 수 있어요 상세 cm로 치수 적어 드릴 테니 가지고 계신 옷들과 비교 후 주문 부탁드릴게요 Detail cut ■ (블랙)스트라이프 ■ (그레이)스트라이프 NAVER REVIEW QUEEN ♥ 3만원 쿠폰 지급 ♥ 매달 '2,4째주' 금요일 베스트 리뷰를 선정하여 '리뷰 퀸'이 되신 고객님 총 3분에게 3만원 상당의 쿠폰을 드립니다. 리뷰 퀸은 착용했을 때의 리얼한 표현과 착용 핏이 잘 보여진 정성스러운 리뷰 중에 베스트 리뷰를 선정합니다. ※ 리뷰에 올라온 포토리뷰의 사진은 홈페이지에 노출될 수 있습니다 ※ ※ 오직 네이버에서 구매해 주신 분들만 해당됩니다 ※ 모노 타임에 게시된 모든 컨텐츠는 저작권법에 의거하여 보호받고 있으며, 모노 타임의 승인 없이 무단 도용 시 경고 없이 저작권법에 의해 법적 조치를 받을 수 있습니다 ※배송의 경우 당일 배송 불가, 2~7일 정도 ★인기 품목 상품, 리오더 상품인 경우 길게는 2주까지 소요될 수 있어요★ 이점 양해 부탁드릴게요. 수제화나 개인 오더 제품인 경우 1~2주 정도 소요될 수 있어요. 모노타임은 CJ 대한통운을 이용하고 있어요. ※교환 및 환불 경우 물건 하자 및 상품 불량은 택배비 착불이에요. 단순 변심은 택배비 본인 부담입니다. 주문 내역에서 반품 요청 후 수거 요청까지 해주세요. CJ 대한통운 택배가 아닌 다른 택배 이용하실 경우 선불로 보내주셔야 합니다. ※제품 문의 평일 : 오후 1시 ~ 오후 6시 주말, 공휴일 휴무 041-751-3637 / 041-753-3637 010-9707-3637\"",
  "stock_quantity" : "1257",
  "sale_price" : "30000",
  "product_no" : "\"5430303268\"",
  "tags" : "\"부드러운니트,오피스룩\"",
  "filename" : "logstash",
  "exposure" : "true",
  "average_review_score" : "4.16",
  "name" : "\"[봄신상 무배] 누리 카라 스트라이프 루즈핏 니트\"",
  "logger" : "agent.command",
  "message" : "stored!"
}

```
