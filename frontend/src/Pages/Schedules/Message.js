import React from "react";
import { Message } from "semantic-ui-react";

const MessagePopUp = () => (
  <Message negative>
    <Message.Header>Nepavyko sukurti</Message.Header>
    <p>
      Tvarkaraščio pradžia numatyta vėliau..? Norite suplanuoti per daug
      pamokų..? Kitos pamokos suplanuotos tuo pačiu metu..? Neteisingai suvesti
      duomenys..?"
    </p>
  </Message>
);

export default MessagePopUp;
