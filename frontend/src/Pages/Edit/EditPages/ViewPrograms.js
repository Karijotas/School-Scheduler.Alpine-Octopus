import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  Button,
  Dropdown,
  Icon,
  Input,
  Pagination,
  Table,
} from "semantic-ui-react";


const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewPrograms() {
  const [activeItem, setActiveItem] = useState("");
  const [nameText, setNameText] = useState("");  
  const [programs, setPrograms] = useState([]);
  const [activePage, setActivePage] = useState(0); 

<<<<<<< HEAD
  const fetchFilterPrograms = async () => {
    fetch(`/api/v1/programs/page/starting-with/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setPrograms(jsonRespone));
  };
=======
    const [activeItem, setActiveItem] = useState('');
    const [activePage, setActivePage] = useState(0)
    const [nameText, setNameText] = useState('')
>>>>>>> b1bf63d00a629d8538c4ace2a6ebd703f84b1c90

  const fetchPrograms = async () => {
    fetch(`/api/v1/programs/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setPrograms(jsonRespones));
  };

<<<<<<< HEAD
  const removeProgram = (id) => {
    fetch("/api/v1/programs/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchFilterPrograms);
  };

 
  useEffect(() => {   
    nameText.length > 0 ? fetchFilterPrograms() : fetchPrograms();
  }, [activePage, nameText]);

  return (
    <div id="programs">
      <Input
        value={nameText}
        onChange={(e) => setNameText(e.target.value)}
        placeholder="Ieškoti pagal pavadinimą"
      />
      {/* <Button onClick={fetchFilterPrograms}>Filtruoti</Button> */}

      <Button
        icon
        labelPosition="left"
        primary
        href="#/create"
        className="controls"
      >
        <Icon name="database" />
        Kurti naują programą
      </Button>
=======
    const fetchPrograms = async () => {
        fetch('/api/v1/programs/page?page=' + activePage)
            .then(response => response.json())
            .then(jsonResponse => setPrograms(jsonResponse));
    };

    const fetchFilterPrograms = async () => {
        fetch('/api/v1/programs/starting-with/' + nameText)
            .then(response => response.json())
            .then(jsonResponse => setPrograms(jsonResponse));
    };

    const removeProgram = (id) => {
        fetch('/api/v1/programs/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchPrograms);
    }


    useEffect(() => {
        fetchPrograms();
    }, [activePage, nameText]);


    return (<div id='programs'>
        <Input value={nameText} onChange={(e) => setNameText(e.target.value)} />
        <Button onClick={fetchFilterPrograms}>Filtruoti</Button>

        {nameText && (<div></div>)}



        <Button icon labelPosition='left' primary href='#/create' className='controls'><Icon name='database' />Kurti naują programą</Button>
>>>>>>> b1bf63d00a629d8538c4ace2a6ebd703f84b1c90

      <Table selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
            <Table.HeaderCell>Programos aprašymas</Table.HeaderCell>
            <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
            <Table.HeaderCell>Veiksmai</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

<<<<<<< HEAD
        <Table.Body>
          {programs.map((program) => (
            <Table.Row key={program.id}>
              <Table.Cell>{program.name}</Table.Cell>
              <Table.Cell>{program.description}</Table.Cell>
              <Table.Cell>{program.modifiedDate}</Table.Cell>
              <Table.Cell collapsing>
                <Button
                  basic
                  primary
                  compact
                  icon="eye"
                  title="Peržiūrėti"
                  active={activeItem === program.id}
                  onClick={console.log("programs/" + program.id)}
                ></Button>
                <Button
                  basic
                  color="black"
                  compact
                  icon="trash alternate"
                  onClick={() => removeProgram(program.id)}
                ></Button>
              </Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>
      <button onClick={()=> setActivePage(0)}> 1 </button>
              <button onClick={()=> setActivePage(1)}> 2 </button> 
              <button onClick={()=> setActivePage(2)}> 3 </button> 
              <button onClick={()=> setActivePage(3)}> 4 </button>      
      {/* <Pagination 
            defaultActivePage={1}
            activePage={activePage}
            onPageChange={onPageChange}
            ellipsisItem={null}
            siblingRange={1}
            totalPages={10}          
            
        />    */}  
=======


        <Table selectable >
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>id</Table.HeaderCell>

                    <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Programos aprašymas</Table.HeaderCell>
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                {programs.map(program => (

                    <Table.Row key={program.id}>
                        <Table.Cell>{program.id}</Table.Cell>
                        <Table.Cell>{program.name}</Table.Cell>
                        <Table.Cell>{program.description}</Table.Cell>
                        <Table.Cell>{program.modifiedDate}</Table.Cell>
                        <Table.Cell collapsing>
                            <Button basic primary compact icon='eye' title='Peržiūrėti' ></Button>
                            <Button basic color='black' compact icon='trash alternate' onClick={() => removeProgram(program.id)}></Button>

                        </Table.Cell>
                    </Table.Row>
                ))}


            </Table.Body>

        </Table>
        <Button onClick={() => setActivePage(0)}>1</Button>
        <Button onClick={() => setActivePage(1)}>2</Button>

        {/* <Pagination
            defaultActivePage={0}
            pointing
            totalPages={3}
            onPageChange={() => setActivePage(0)}
        /> */}
>>>>>>> b1bf63d00a629d8538c4ace2a6ebd703f84b1c90
    </div>
  );
}