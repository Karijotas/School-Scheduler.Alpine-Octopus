import React from 'react'
import { Button, Icon, Modal } from 'semantic-ui-react'
import { useParams } from "react-router-dom"
import { useEffect, useState } from "react";



function exampleReducer(state, action) {
  switch (action.type) {
    case 'close':
      return { open: false }
    case 'open':
      return { open: true, size: action.size }
    default:
      throw new Error('Unsupported action...')
  }
}

const ModalExampleSize = () => {

    const params = useParams();
    const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [active, setActive] = useState(false);
  const [subject, setSubject] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [lesson, setLesson] = useState({
    id: "",
    name: "",
    startTime: "",
    endTime: "",
  });

  const [state, dispatch] = React.useReducer(exampleReducer, {
    open: false,
    size: undefined,
  })
  const { open, size } = state

  const createLessonOnSchedule = (props) => {
    fetch(
      `/api/v1/schedule/${params.id}/create/${props.id}/${props.startTime}/${props.endTime}`,
      {
        method: "PATCH",
      }
    ).then(response => {
        if(response.status===400) {
            setErrorMessage(response.errorMessage)
        } else if (response.status.ok){
            return response.json();
        }
    }).then(setActive(true));
    setLesson({});
    setStartTime("");
    setEndTime("");
    
  };
  
  return (
    <>
      <Button onClick={() => dispatch({ type: 'open', size: 'mini' })}>
        Mini
      </Button>

      <Modal
        size={size}
        open={open}
        onClose={() => dispatch({ type: 'close' })}
      >
        <Modal.Header>Delete Your Account</Modal.Header>
        <Modal.Content>
          <p>Are you sure you want to delete your account</p>
        </Modal.Content>
        <Modal.Actions>
          <Button negative onClick={() => dispatch({ type: 'close' })}>
            No
          </Button>
          <Button positive onClick={() => dispatch({ type: 'close' })}>
            Yes
          </Button>
        </Modal.Actions>
      </Modal>
    </>
  )
}

export default ModalExampleSize