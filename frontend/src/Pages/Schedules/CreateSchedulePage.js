import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment, Select } from "semantic-ui-react";


const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateSchedule() {

  const listUrl = useHref('/view/groups');

  const [hide, setHide] = useState(false)
  
  const [groups, setGroups] = useState([])

  const [groupId, setGroupId] = useState(null)

  const [startDate, setStartDate] = useState([])
 

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      let info = result.json() 
      .then((jsonResponse) => window.location = listUrl);
    } else {
      window.alert("Nepavyko sukurti: pavadinimas turi būti unikalus!");    
    }
  };

  const createSchedule = () => {
    fetch(
      '/api/v1/schedule?groupId=' + groupId, {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        
        
       
      }),
    }).then(applyResult);

  };
  useEffect(() => {
    fetch("/api/v1/groups/")
      .then((response) => response.json())
      .then((data) =>
        setGroups(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [groups]);


  return (<div className="create-new-page">

    

    <Grid columns={2} >
      <Grid.Column width={2} id='main'>
       
      </Grid.Column>
      <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
        <Segment id='segment' color='teal'>

          <Form >
            <Form.Field>
                <label>Grupe su programa</label>
                <Select options={groups} placeholder='Grupe su programa' onChange={(e, data) => setGroupId(data.value)} />
            </Form.Field>

            <Form.Field >
              <label>Data nuo</label>
              <input name="name"  placeholder='data nuo' value={Date} onChange={(e) => setStartDate} />
            </Form.Field>
              
              
            <div ><Button icon labelPosition="left" className="" as={NavLink} exact to='/view/groupsSchedules'><Icon name="arrow left" />Atgal</Button>
              <Button type='submit' className="controls" id='details' onClick={createSchedule}>Sukurti</Button></div>
          </Form>
      

        </Segment>
      </Grid.Column>
    </Grid>
  </div>
  );
}
