import React, { useState } from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import _ from 'lodash';

const html = value => {
  return {__html: value.substr(0, 100)};
}

const firstImg = value => {
  return _.head(JSON.parse(value));
}

const MONEY_PATTEN = /(^[+-]?\d+)(\d{3})/;

const dispNumber = num => {

  if(!num)
    return 0

  num += '';

  while(MONEY_PATTEN.test(num)) {
    num = num.replace(MONEY_PATTEN, '$1' + ',' + '$2');
  }

  return num;
}

const ItemCard = item => {

  return (
    <Card>
      <Card.Img variant="top" src={firstImg(item.images)} />

      <Card.Body>
        <Card.Title>{item.name} {dispNumber(item.pc_discount_price)}원 </Card.Title>
        <Card.Subtitle> {item.naver_category}/{item.style_name} </Card.Subtitle>
        <Card.Text dangerouslySetInnerHTML={html(item.content_text)}/>
        <Card.Subtitle><b> visit: {dispNumber(item.view_count_from_window)}</b><br/><b>tags</b>: {item.tags} </Card.Subtitle>
      </Card.Body>
    </Card>
  );

};

function ItemList({items}) {

  const chunks = _.chunk(items, 4);

  return (
    <>
      {chunks.map(list =>
        <Row>
          {list.map(i => <Col md="3" xs="6" ><ItemCard {...i} /></Col>)}
        </Row>
      )}
    </>
  );

};

export default ItemList;
