import React from 'react'
import { Button } from 'semantic-ui-react'

const OkCancel = () => (
  <Button.Group size='small'>
   <Button  positive >Patvirtinti</Button>
     <Button negative>Atšaukti</Button>
  </Button.Group>
)

export default OkCancel
