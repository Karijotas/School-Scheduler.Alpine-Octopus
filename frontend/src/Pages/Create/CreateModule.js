import React, { Component } from 'react'
import { Button, Form,Card , Grid, Container} from 'semantic-ui-react'
import { useState, useEffect } from "react";
import './CreateModule.css'
import { useHref } from "react-router-dom";
import { ModuleList } from '../Edit/EditPages/ModuleList';
import EditMenu from '../Edit/EditMenu';


export function CreateModule(){

  const [subjects, setSubjects] = useState([]);

  const fetchSubjects = async () => {
    fetch("/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  };

  useEffect(() => {
    fetchSubjects();
  }, []);


  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const listUrl = useHref("/edit");

  const clear = () => {
    setName("");
    setDescription("");
  };

  const applyResult = (result) => {
    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurti:" + result.status);
    }
  };

  const createModule = () => {
    fetch("api/v1/modules", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        
      }),
    })
      .then(applyResult)
      .then(() => (window.location = listUrl));
  };

    return (
      <Container style={{width: "25rem" , marginTop: '5rem'}} >
        <Grid centered className='forma' >
          <Card style= {{padding: '1rem'}}>
            <Form>
            <Form class="Group">
              <Form.Field>
                <label>Modulio pavadinimas</label>
                <input placeholder='Modulio pavadinimas' value={name}
                onChange={(e) => setName(e.target.value)}/>
              </Form.Field>
              <Form.TextArea label='Apie modulį' placeholder='Aprašykite modulį...' value={description}
                onChange={(e) => setDescription(e.target.value)} />
            </Form>
            <Form.Group grouped >
            <label>Pasirinktini dalykai:</label>
            {subjects.map((subject) => (
            <Form.Field label={subject.name} control='input' type='checkbox' key={subject.id}/>
            ))}
                </Form.Group>
            <Button type='submit' onClick={createModule}>Išsaugoti</Button>
            <Button type='submit' onClick={<EditMenu/>}>Grįžti</Button>
              </Form>
          </Card>
        </Grid>
      </Container>
      )
    }