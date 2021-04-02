# Elastic Stack

 이전 업무에서 적용한 ELK를 간단히 재구성한 것입니다.


## ELK Install

1. Elasticsearch
1. Kibana 
1. Logstash


## Agent

에이젼트는 네이버 쇼핑 윈도우에서 최상 Top 100건의 데이터를  
스크랩한다.

![](/images/naver-window.png)

* page, pageSize 를 파라미터로 사용한다  
* agent가 스크랩하는 상품정보는 로그로 남긴다  
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
