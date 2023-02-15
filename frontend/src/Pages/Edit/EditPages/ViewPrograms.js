import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import { Button, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewPrograms() {

    const [activeItem, setActiveItem] = useState('');
    const [activePage, setActivePage] = useState(0)
    const [nameText, setNameText] = useState('')

    const [programs, setPrograms] = useState([]);

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
        nameText.length > 0 ? fetchFilterPrograms() : fetchPrograms();
    }, [activePage, nameText]);


    return (<div id='programs'>
        <Input value={nameText} onChange={(e) => setNameText(e.target.value)} />
        {nameText && (<div></div>)}



        <Button icon labelPosition='left' primary href='#/create' className='controls'><Icon name='database' />Kurti naują programą</Button>




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
                            <Button basic color='black' compact  icon='trash alternate' onClick={() => removeProgram(program.id)}></Button>

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
    </div>
    )
}