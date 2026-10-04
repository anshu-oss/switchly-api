package live.switchly.api.service;

import live.switchly.api.exception.NotFoundException;
import live.switchly.api.model.Organization;
import live.switchly.api.model.Project;
import live.switchly.api.repository.OrganizationRepository;
import live.switchly.api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final OrganizationRepository organizationRepository;
    private final ProjectRepository projectRepository;

    public ProjectService(
            OrganizationRepository organizationRepository,
            ProjectRepository projectRepository) {
        this.organizationRepository = organizationRepository;
        this.projectRepository = projectRepository;
    }

    public Project create(UUID organizationId, String name) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Organization " + organizationId + " not found"));

        Project project = new Project(
                UUID.randomUUID(),
                organization.getId(),
                name
        );

        return projectRepository.save(project);
    }

    public List<Project> getAllForOrganization(UUID organizationId) {

        organizationRepository.findById(organizationId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Organization " + organizationId + " not found"));

        return projectRepository.findByOrganizationId(organizationId);
    }

    public Project getById(UUID id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Project " + id + " not found"));
    }
}