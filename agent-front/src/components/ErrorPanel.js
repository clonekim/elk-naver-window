import { Alert , Button } from 'react-bootstrap';

function ErrorPanel ({ errors, clearError }) {

  if(errors) {

    return (
      <Alert variant="danger" onClose={() => clearError()} dismissible>
          <Alert.Heading>Error</Alert.Heading>
            <ul>
              { errors.map(i => <li key={i.key} >{i.key}: {i.message} </li>) }
            </ul>
        </Alert>
    );

  }else {
    return null;
  }
}


export default ErrorPanel;
