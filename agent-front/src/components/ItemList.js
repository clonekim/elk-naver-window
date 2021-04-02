import React, { useState } from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import _ from 'lodash';

const html = value => {
  return {__html: value.substr(0, 100)};
}

const firstImg = value => {
  return _.head(JSON.parse(value));
}

const ItemCard = item => {

  return (
    <Card>
      <Card.Img variant="top" src={firstImg(item.images)} />

      <Card.Body>
        <Card.Title>{item.name} {item.pc_discount_price} </Card.Title>
        <Card.Subtitle> {item.naver_category}/{item.style_name} </Card.Subtitle>
        <Card.Text dangerouslySetInnerHTML={html(item.content_text)}/>
        <Card.Subtitle><b> visit: {item.view_count_from_window}</b><br/><b>tags</b>: {item.tags} </Card.Subtitle>
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
          {list.map(i => <Col sm="3"><ItemCard {...i} /></Col>)}
        </Row>
      )}
    </>
  );

};

export default ItemList;
