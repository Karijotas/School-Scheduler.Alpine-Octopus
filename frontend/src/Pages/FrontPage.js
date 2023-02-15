
import * as React from "react";
import { Placeholder, Rail, Segment } from "semantic-ui-react";
import './FrontPage.css'


const items = [
  'Čia galite peržiūrėti, suvesti, atnaujinti bei ištrinti duomenis',
  'Veikia pagingas bei filtravimas',
]

function Front() {
  return (
    
<div>
<Placeholder>
    <Placeholder.Header image>
      <Placeholder.Line />
      <Placeholder.Line />
    </Placeholder.Header>
    <Placeholder.Paragraph>
      <Placeholder.Line />
      <Placeholder.Line />
      <Placeholder.Line />
      <Placeholder.Line />
    </Placeholder.Paragraph>
  </Placeholder></div>
  );
}

export default Front;
