# Elastic Stack

 이전 업무에서 적용한 ELK를 간단히 재구성한 것입니다.

## 가이드

[가이드북-한글](https://esbook.kimjmin.net/)

## 스택 구성

![](/images/elk-map.png)

사전에 아래와 같이 ELK Stack의 구성요소를 설치한다  
1. Elasticsearch
1. Kibana 
1. Logstash

1,2 번은 설치 후 각각 서버를 시작한다  

## Agent

해당 에이젼트 프로그램의 소스는 agent에  있다  
clojure로 작성 되었지만 빌드결과물(fatjar)이 build 디렉토리에 존재하므로  
파일을 내려받아 바로 실행 할 수 있다.  

> 에이젼트가 하는 일은 다음과 같이 아주 단순하다.    
 1. [에이젼트는 네이버 쇼핑 윈도우에서 최상 Top 100건의 데이터를 스크랩한다.](request.md)
 2. [스크랩과 동시에 로그파일에 상품정보를 출력한다.](agent-log.md)


> 실행방법  
 아래와 같이 실행 후 브라우저로 http://localhost:8000에 접속한다   
```
 java -jar agent.jar
```
![프론트 화면](/images/front.png)

buffer에 10을 입력 후 maxitem에 100을 입력 후 Menu ID를 여성을 고르자  
페이지당 10개 씩 총 100개의 상품을 스크랩한다

> 로그위치  

```
agent를 실행한 위치에 logstash.log가 생성된다
```



## Logstash

아래와 같이 naver.conf 파일을 작성 한다.  
path에 agent의 로그가 출력하는 경로를 적어준다  
output 에 로그가 최종적으로 전송될 위치, 엘라스틱 서버를 지정한다.  
이제 agent에서 상품을 스크랩핑 해보자  
그러면 엘라스틱서치 에게 'naver-window-top-날짜' 라는 인덱스로 형성이 된다.  
filter의 역할은 [agent가 생성하는 로그 중](agent-log.md)에서 필요없는 필드를 제외시킨다  

*naver.conf*
```
input {
  file {
    path  => "/agent/logstash.log"
      start_position => "beginning"
      stat_interval => 1
      codec => "json"
  }
}

filter {
  mutate {
    remove_field => ["host", "path", "level", "filename", "logger", "timestamp", "images", "message"]
  }

}

output {
  elasticsearch {
    hosts => ["127.0.0.1:9200"]
      index => "naver-window-top-%{+YYYY.MM.dd}"
  }
}
```

> 실행
```
logstash -f naver.conf
```

## Kibana

키바나에 접속하여 저장된 데이터로 시각화 작업을 해보자  
http://localhost:5601 로 접속한다.


> Visualize Library 로 가서 간단한 heatmap를 표현해보자  
aggregation based를 선택한다  

![](/images/visual-step1.png)

1. Buckets  Add 클릭 후
2. X-axis 에는 업체명 (channel_name.keyword) 을 선택  
3. Y-axis 에는 네이버 카테고리 (naver_category.keyword)를 선택

![](/images/bucket-add.png)

![결과](/images/heatmap.png)

> 네이버 카테고리를 태그클라우드로 보자  
aggregation based에서 선택한다  


![](/images/tag-cloud.png)
