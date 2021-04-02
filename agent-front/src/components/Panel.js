import React, { useState } from 'react';
import { Modal, Form, Button, Col, Row } from 'react-bootstrap';

function Panel({sendTrigger, setErrors, setItems, socketStatus }) {

  const [buffer, setBuffer] = useState('');
  const [maxitem, setMaxItem] = useState('');
  const [menuid, setMenuId] = useState('');

  const submitHandler = event => {
    event.preventDefault();

    const validations = [];

    if(!buffer || isNaN(buffer))
      validations.push({key: 'buffer', message: '버퍼사이즈를 입력하세요'});

    if(!maxitem || isNaN(maxitem))
      validations.push({key: 'maxitem', message: '수집할 아이템의 총 개수를 입력하세요'});

    if(!menuid)
      validations.push({key: 'menuid', message: '메뉴를 선택하세요'});

    if(validations.length > 0) {
      setErrors(validations)
      return
    }

    sendTrigger({buffer, maxitem, menuid}).then(() => {
      console.log('ok')
    })

  };


  return (
    <Form onSubmit={submitHandler}>
      <Row>
        <Col>
          <Form.Group>
            <Form.Label>Buffer</Form.Label>
            <Form.Control type="text"  onChange={e => setBuffer(e.target.value)} value={buffer}/>
          </Form.Group>
        </Col>

        <Col>
          <Form.Group>
            <Form.Label>Max Items</Form.Label>
            <Form.Control type="text" onChange={e => setMaxItem(e.target.value)} value={maxitem}/>
          </Form.Group>
        </Col>

        <Col>
          <Form.Group>
            <Form.Label>Menu ID</Form.Label>
            <Form.Control as="select" onChange={e => setMenuId(e.target.value)} value={menuid}>
              <option></option>
              <option value="10000531">여성</option>
              <option value="10000623">남성</option>
              <option value="10000694">수제화</option>
              <option value="10010073">주얼리</option>
            </Form.Control>
          </Form.Group>
        </Col>
      </Row>

      <Row>
        <Col sm="12">
          <Button type="submit" variant="primary">Send</Button> &nbsp;
          <Button variant="secondary" onClick={() => setItems([]) }>Reset result</Button>
          <div className="float-right">
            {socketStatus}
          </div>
        </Col>
      </Row>
    </Form>

  );

}


export default Panel;
