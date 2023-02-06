import React from 'react'
import { Button, Icon, Image, Modal } from 'semantic-ui-react'
import ButtonExampleConditionals from '../../Components/OkCancel'

function ModalScrollingExample() {
    const [open, setOpen] = React.useState(false)

    return (<div>

        <Modal
            closeIcon
            open={open}
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)} alternate
            trigger={<Button icon='pencil alternate' basic ></Button>}
        >
            <Modal.Header>Modal Header</Modal.Header>
            <Modal.Content >
                <Modal.Description>
                    <p>


                        Text Text Text
                    </p>


                </Modal.Description>
            </Modal.Content>
            <Modal.Actions>
                {/* 
        <Button primary onClick={() => setOpen(false)}>
          Tvarkaraščiai <Icon name='right chevron' />
        </Button>  */}
                <ButtonExampleConditionals />
            </Modal.Actions>
        </Modal>
        <Button basic icon='remove' negative></Button>
    </div>
    )
}

export default ModalScrollingExample
