# Elastic Stack

 이전 업무에서 적용한 ELK를 간단히 재구성한 것입니다.

## 가이드

[가이드북-한글](https://esbook.kimjmin.net/)

## ELK Install

1. Elasticsearch
1. Kibana 
1. Logstash


## Agent

에이젼트는 네이버 쇼핑 윈도우에서 최상 Top 100건의 데이터를  
스크랩한다.

![](/images/naver-window.png)

* [page, pageSize, menuId 를 파라미터로 사용한다](request.md)  
* [agent가 스크랩하는 상품정보는 로그로 남긴다](agent-log.md)  
* log format은 JSON 형식


![](/images/elk-map.png)


## Logstash

Input에서 해당 파일을 감시한다.

```
input {
  file {
    path  => "/agent/log/logstash.log"
      start_position => "beginning"
      stat_interval => 1
      codec => "json"
  }
}

filter {
  mutate {
    remove_field => ["host", "path", "level", "filename", "logger", "timestamp", "images"]
  }

}

output {
  elasticsearch {
    hosts => ["127.0.0.1:9200"]
      index => "naver-window-top-%{+YYYY.MM.dd}"
  }
}
```

## Kibana

http://localhost:5601 로 접속


* Visualize Library 로 가서 간단힌 heatmap를 표현해보자
![](/images/visual-step1.png)
* Buckets  Add 클릭 후
* X-axis 에는 업체명 을 선택  
* Y-axis 에는 네이버 카테고리를 선택
 ![](/images/bucket-add.png)

![결과](/images/heatmap.png)

네이버 카테고리를 태그클라우드로 보자

![](/images/tag-cloud.png)
