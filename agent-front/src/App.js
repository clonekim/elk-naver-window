import { useState } from 'react';
import useWebSocket from 'react-use-websocket';
import ErrorPanel from './components/ErrorPanel';
import Panel from './components/Panel';
import ItemList from './components/ItemList';
import useAgent from './hook/useAgent';
import { Container, Navbar } from 'react-bootstrap';
import './Bootstrap.scss';

function App() {
  const url = 'ws://localhost:8000/socket';

  const [socketStatus, setSocketStatus ] = useState(null);
  const { items, setItems,  sendTrigger, errors, setErrors, clearError } = useAgent();

  const {lastMessage } = useWebSocket(url, {
    onOpen: () => setSocketStatus('server connected'),
    onMessage: ( event) => {

      if(event.data) {
        const data = JSON.parse(event.data);

        if(!data.status)
          setItems(items.concat( data.data ));
      }
    },
    shouldReconnect: (e) => true
  })


  return (
      <>
        <Navbar bg="light">
          <Navbar.Brand> Agent Front</Navbar.Brand>
        </Navbar>

        <Container fluid="md">
          <ErrorPanel errors={errors} clearError={clearError} />
          <Panel sendTrigger={sendTrigger} setErrors={setErrors}  setItems={setItems} socketStatus={socketStatus} />
          <ItemList items={items}/>
        </Container>
      </>

  );
}

export default App;
