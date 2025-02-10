import React, { useState, useEffect } from 'react';
import { Container, Card, ListGroup, Image, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const OtherBlogs = () => {
  const [blogs, setBlogs] = useState([]);
  const token = localStorage.getItem('jwtToken');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBlogs = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/otherBlogs', {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        setBlogs(data);
      } catch (error) {
        console.error('Error fetching blogs:', error);
      }
    };

    if (token) {
      fetchBlogs();
    }
  }, [token]);

  const handleLogout = () => {
    localStorage.removeItem('jwtToken');
    navigate('/'); 
  };

  return (
    <Container className="mt-5">
     
      <Button variant="danger" onClick={handleLogout} className="mb-4">
        Logout
      </Button>
      <h2 className="mb-4">Other Blogs</h2>
      <ListGroup variant="flush">
        {blogs.map((blog) => (
          <Card key={blog._id || blog.id} className="mb-3 shadow-sm">
            <Card.Body>
              <Card.Title>Title -{blog.title}</Card.Title>
              <Card.Text>Content - {blog.content}</Card.Text>
              {blog.imageData && (
                <Image
                  src={`data:image/jpeg;base64,${blog.imageData}`}
                  alt={blog.title}
                  className="mt-3"
                  style={{ width: '50%', height: 'auto' }}
                />
              )}
            </Card.Body>
          </Card>
        ))}
      </ListGroup>
    </Container>
  );
};

export default OtherBlogs;
