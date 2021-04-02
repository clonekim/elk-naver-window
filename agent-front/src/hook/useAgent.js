import { useState } from 'react';
import axios from 'axios'


function useAgent() {

  const [items, setItems] = useState([]);
  const [errors, setErrors ] = useState(null);

  const sendTrigger = async params => {
    const response = await axios.post('/trigger', Object.assign(params, {command: 'collect'}))
                                .then(res => {
                                  setItems([]);
                                  setErrors(null);
                                  return res.data;
                                }).catch(err => {
                                  console.log(err);
                                  setErrors([...errors||[], {key: 'server', message: err.response.data.message }]);
                                });
  }


  const clearError = () => setErrors(null);


  return {
    items,
    setItems,
    errors,
    setErrors,
    clearError,
    sendTrigger
  }
};


export default useAgent;
