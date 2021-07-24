import {Navbar, Nav, Container} from "react-bootstrap";

const TopNavBar = () => {
    return (
        <>
            <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant={'dark'}>
                <Container>
                    <Navbar.Toggle aria-controls={'responsive-navbar-nav'}/>
                    <Navbar.Collapse id={'responsive-navbar-nav'}>
                        <Nav>
                            <Nav.Link href={'/'}>GraphList</Nav.Link>
                            <Nav.Link href={'/login'}>Login</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default TopNavBar;